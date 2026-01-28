package org.hit.hradar.domain.user.command.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserAccountRequest {

    @NotBlank(message = "아이디를 입력해주세요") @Size(max = 50)
    private String loginId;

    @NotBlank(message = "이름을 입력해주세요") @Size(max = 100)
    private String name;

    @Email @NotBlank(message = "이메일을 입력해주세요") @Size(max = 50)
    private String email;

}
