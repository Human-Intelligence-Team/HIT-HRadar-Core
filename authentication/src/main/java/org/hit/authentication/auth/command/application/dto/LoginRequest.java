package org.hit.authentication.auth.command.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "회사코드를 입력해주세요.")
    @Size(max = 30, message = "회사코드의 최대 길이는 {max}입니다.")
    private String companyCode;

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(max = 30, message = "아이디 최대 길이는 {max}글자입니다.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, message = "비밀번호 최소 길이는 {min}글자입니다.")
    private String password;
}
