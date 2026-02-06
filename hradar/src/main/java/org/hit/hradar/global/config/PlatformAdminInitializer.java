package org.hit.hradar.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * 플랫폼 관리자 계정을 애플리케이션 시작 시 자동으로 생성합니다.
 * JPA Auditing을 우회하기 위해 네이티브 쿼리를 사용합니다.
 * 
 * created_by, updated_by = 0은 "시스템 자동 생성"을 의미합니다.
 * (부트스트래핑 문제: 최초 admin 계정 생성 시 생성자가 없음)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PlatformAdminInitializer {

        /**
         * 시스템 계정 ID (부트스트래핑 시 사용)
         * 플랫폼 관리자 생성 시 created_by/updated_by는 이 값을 사용합니다.
         */
        private static final Long SYSTEM_ACCOUNT_ID = 0L;

        private final JdbcTemplate jdbcTemplate;
        private final PasswordEncoder passwordEncoder;

        @EventListener(ApplicationReadyEvent.class)
        @Transactional
        public void initializePlatformAdmin() {
                // 이미 플랫폼 관리자가 존재하면 생성하지 않음
                Integer count = jdbcTemplate.queryForObject(
                                "SELECT COUNT(*) FROM user_account WHERE login_id = ?",
                                Integer.class,
                                "platform_admin");

                if (count != null && count > 0) {
                        log.info("✅ Platform admin already exists. Skipping initialization.");
                        return;
                }

                try {
                        // 1. 플랫폼 관리용 더미 회사 생성
                        jdbcTemplate.update(
                                        "INSERT INTO company (application_id, company_code, name, ceo_name, company_email, "
                                                        +
                                                        "biz_no, address, company_telephone, founded_date, status, is_deleted, created_at, updated_at, created_by, updated_by) "
                                                        +
                                                        "VALUES (0, 'PLATFORM', 'HRadar Platform System', 'System Admin', 'platform@hradar.com', "
                                                        +
                                                        "'000-00-00000', 'System Internal', '000-0000-0000', ?, 'APPROVED', 'N', NOW(), NOW(), ?, ?)",
                                        LocalDate.now(), SYSTEM_ACCOUNT_ID, SYSTEM_ACCOUNT_ID);

                        Long companyId = jdbcTemplate.queryForObject(
                                        "SELECT LAST_INSERT_ID()",
                                        Long.class);
                        log.info("📦 Platform company created: ID={}", companyId);

                        // 2. 플랫폼 관리자 사원 생성
                        jdbcTemplate.update(
                                        "INSERT INTO employee (com_id, name, employee_no, email, hire_date, type, is_deleted, created_at, updated_at, created_by, updated_by) "
                                                        +
                                                        "VALUES (?, 'Platform Administrator', 'PLATFORM-001', 'admin@hradar.com', ?, 'WORKING', 'N', NOW(), NOW(), ?, ?)",
                                        companyId, LocalDate.now(), SYSTEM_ACCOUNT_ID, SYSTEM_ACCOUNT_ID);
                        Long employeeId = jdbcTemplate.queryForObject(
                                        "SELECT LAST_INSERT_ID()",
                                        Long.class);
                        log.info("👤 Platform employee created: ID={}", employeeId);

                        // 3. 플랫폼 관리자 계정 생성
                        String encodedPassword = passwordEncoder.encode("hradar2026!");
                        jdbcTemplate.update(
                                        "INSERT INTO user_account (com_id, company_code, employee_id, login_id, email, password, name, role, status, is_deleted) "
                                                        +
                                                        "VALUES (?, 'PLATFORM', ?, 'platform_admin', 'admin@hradar.com', ?, 'Platform Administrator', 'admin', 'ACTIVE', 'N')",
                                        companyId, employeeId, encodedPassword, SYSTEM_ACCOUNT_ID, SYSTEM_ACCOUNT_ID);

                        log.info("✅ Platform admin account created successfully!");
                        log.warn("⚠️  Default credentials: platform_admin / hradar2026!");
                        log.warn("⚠️  Please change the password immediately after first login!");

                } catch (Exception e) {
                        log.error("❌ Failed to initialize platform admin: {}", e.getMessage(), e);
                }
        }
}
