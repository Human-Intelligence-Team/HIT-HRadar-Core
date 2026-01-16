package org.hit.notification.platform.common.aop;

import org.hit.notification.platform.common.dto.AuthUser;
import org.hit.notification.platform.common.exception.UnauthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
                && parameter.getParameterType().equals(AuthUser.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        String userId = webRequest.getHeader("X-User-Id");
        String role = webRequest.getHeader("X-User-Role");
        String companyId = webRequest.getHeader("X-Company-Id");

        if (userId == null || role == null || companyId == null) {
            throw new UnauthorizedException("인증 헤더 누락");
        }

        return new AuthUser(
                Long.valueOf(userId),
                role,
                Long.valueOf(companyId)
        );
    }
}
