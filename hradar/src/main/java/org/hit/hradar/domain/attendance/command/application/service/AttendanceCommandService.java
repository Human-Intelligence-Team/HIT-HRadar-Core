package org.hit.hradar.domain.attendance.command.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hit.hradar.domain.attendance.IpPolicyErrorCode;
import org.hit.hradar.domain.attendance.command.application.dto.response.AttendanceCheckResponse;
import org.hit.hradar.domain.attendance.command.domain.aggregate.*;
import org.hit.hradar.domain.attendance.command.domain.policy.IpAccessValidator;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceRepository;
import org.hit.hradar.domain.attendance.command.infrastructure.AttendanceAuthLogJpaRepository;
import org.hit.hradar.domain.attendance.command.infrastructure.AttendanceWorkLogJpaRepository;
import org.hit.hradar.domain.attendance.command.infrastructure.AttendanceWorkPlanJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceCommandService {

    private final AttendanceRepository attendanceRepository;
    private final IpAccessValidator ipAccessValidator;
    private final AttendanceWorkLogJpaRepository workLogRepository;
    private final AttendanceAuthLogJpaRepository authLogRepository;
    private final AttendanceWorkPlanJpaRepository workPlanRepository;

    public AttendanceCheckResponse processAttendance(
            Long empId,
            Long comId,
            String clientIp) {
        log.error(">>> [DEBUG] AttendanceCommandService.processAttendance CALLED (v2) - empId: {}, clientIp: {}", empId,
                clientIp);
        // 1. IP 검증
        log.info("clientIp={}", clientIp);

        if (!ipAccessValidator.validate(comId, clientIp)) {
            throw new BusinessException(IpPolicyErrorCode.IpRange_NOT_FOUND);
        }
        log.info("clientIp={}", clientIp);

        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        log.info("clientIp={}", clientIp);

        // 2. 오늘 근태 조회 or 생성
        Attendance attendance = attendanceRepository
                .findByEmpIdAndWorkDate(empId, today)
                .orElseGet(() -> attendanceRepository.save(new Attendance(empId, today)));

        // 3. 승인된 근무계획 조회
        Optional<AttendanceWorkPlan> approvedPlan = workPlanRepository
                .findByEmpIdAndStatusAndStartAtLessThanEqualAndEndAtGreaterThanEqual(
                        empId,
                        AttendanceApprovalStatus.APPROVED,
                        now,
                        now);

        // 4. 현재 열려 있는 로그 확인
        Optional<AttendanceWorkLog> openedLog = workLogRepository.findTopByAttendanceIdAndEndAtIsNullOrderByStartAtDesc(
                attendance.getAttendanceId());

        String attendanceStatusType;
        String workType;
        String workLocation;
        LocalDateTime checkInTime;

        AttendanceWorkPlan plan = approvedPlan.orElse(null);

        if (openedLog.isEmpty()) {
            // ===== 출근 =====
            attendanceStatusType = "CHECK_IN";

            workType = (plan != null && plan.getWorkType() != null)
                    ? plan.getWorkType()
                    : attendance.getWorkType();

            workLocation = (plan != null && plan.getLocation() != null)
                    ? plan.getLocation()
                    : "OFFICE";

            AttendanceWorkLog log = new AttendanceWorkLog(
                    attendance.getAttendanceId(),
                    WorkLogType.CHECK_IN,
                    now,
                    workLocation);
            workLogRepository.save(log);

            attendance.changeWorkType(workType);
            checkInTime = now;

        } else {
            // ===== 퇴근 =====
            attendanceStatusType = "CHECK_OUT";

            AttendanceWorkLog log = openedLog.get();
            log.close(now);
            workLogRepository.save(log);

            workType = attendance.getWorkType();
            workLocation = log.getLocation();
            checkInTime = log.getStartAt();
        }

        // 5. 인증 로그 저장
        authLogRepository.save(
                new AttendanceAuthLog(attendance.getAttendanceId(), clientIp));

        // 직원 정보 / 초과근무는 아직 자리표시자
        return new AttendanceCheckResponse(
                workType,
                today,
                checkInTime,
                workLocation,
                "NONE",
                clientIp,
                attendanceStatusType);
    }
}
