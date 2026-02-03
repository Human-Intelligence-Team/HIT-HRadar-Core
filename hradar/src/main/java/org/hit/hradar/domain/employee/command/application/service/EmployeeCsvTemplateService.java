package org.hit.hradar.domain.employee.command.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 사원 일괄 등록을 위한 CSV 템플릿 제공 서비스
 */
@Service
@RequiredArgsConstructor
public class EmployeeCsvTemplateService {

    /**
     * 사원 등록 CSV 템플릿 파일의 바이트 배열을 생성합니다.
     * <p>
     * 엑셀에서 한글 깨짐 방지를 위해 BOM(Byte Order Mark)을 포함합니다.
     * </p>
     *
     * @return CSV 파일 내용 (byte[])
     */
    public byte[] getTemplateBytes() {
        try (java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream()) {
            // UTF-8 BOM 추가 (엑셀 호환성)
            baos.write(0xEF);
            baos.write(0xBB);
            baos.write(0xBF);

            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(baos, StandardCharsets.UTF_8))) {
                // 헤더 작성
                // 필수: 이름, 이메일, 전화번호, 사번, 로그인ID, 비밀번호
                // 선택: 입사일, 부서명, 직위명, 성별, 생년월일
                String[] headers = {
                        "이름(필수)", "이메일(필수)", "전화번호(필수)", "사번(필수)", "로그인ID(필수)", "비밀번호(필수)",
                        "입사일(YYYY-MM-DD)", "부서명", "직위명", "성별(M/F)", "생년월일(YYYY-MM-DD)"
                };
                writer.println(String.join(",", headers));

                // 예시 데이터 작성 (사용자 가이드용)
                String[] example = {
                        "홍길동", "hong@example.com", "010-1234-5678", "2024001", "hong123", "password123!",
                        "2024-01-01", "개발팀", "사원", "M", "1990-01-01"
                };
                writer.println(String.join(",", example));
            }
            // Writer가 닫히면서 flush 수행됨

            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("CSV 템플릿 생성 중 오류가 발생했습니다.", e);
        }
    }
}
