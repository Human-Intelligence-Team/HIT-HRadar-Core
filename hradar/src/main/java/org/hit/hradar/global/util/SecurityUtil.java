package org.hit.hradar.global.util;


import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.auth.AuthErrorCode;
import org.hit.hradar.global.dto.LoginUserInfo;
import org.hit.hradar.global.exception.BusinessException;
import org.hit.hradar.global.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    public static LoginUserInfo getLoginUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED_USER);
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails userDetails) {
            return new LoginUserInfo(userDetails.getAccId(), userDetails.getUsername(),
                    userDetails.getUserRole());
        }

        throw new BusinessException(AuthErrorCode.UNAUTHORIZED_USER);
    }


}
