package org.hit.hradar.domain.employee.command.application.service;

import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

/**
 * 사원 일괄 등록을 위한 엑셀 템플릿 제공 서비스
 */
@Service
@RequiredArgsConstructor
public class EmployeeCsvTemplateService {

    /**
     * 사원 등록 CSV 템플릿 파일의 바이트 배열을 생성합니다.
     * BOM(Byte Order Mark)을 추가하여 엑셀에서 한글이 깨지지 않도록 합니다.
     *
     * @return CSV 파일 내용 (byte[])
     */
    public byte[] getTemplateBytes() {
        // UTF-8 BOM
        byte[] bom = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
        String csvContent = "이름(필수),이메일(필수),전화번호,사번(필수),로그인ID(필수),비밀번호(필수),입사일(YYYY-MM-DD),부서명,직위명,성별(M/F),생년월일(YYYY-MM-DD)\n"
                +
                "예시: 홍길동,user@example.com,010-1234-5678,2024001,user01,pw1234!,2024-01-01,개발팀,사원,M,1990-01-01";

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            baos.write(bom);
            baos.write(csvContent.getBytes(StandardCharsets.UTF_8));
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("CSV 템플릿 생성 중 오류가 발생했습니다.", e);
        }
    }
}
