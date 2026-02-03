package org.hit.hradar.global.aop;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.global.context.RequestUserContext;
import org.hit.hradar.global.dto.AuthUser;
import org.hit.hradar.global.exception.UnauthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final RequestUserContext requestUserContext;

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
        String employeeId = webRequest.getHeader("X-Employee-Id");

        if (userId == null || role == null || companyId == null || employeeId == null) {
            throw new UnauthorizedException("인증 헤더 누락");
        }

        AuthUser authUser = new AuthUser(
                Long.valueOf(userId),
                role,
                Long.valueOf(companyId),
                Long.valueOf(employeeId)
        );

        requestUserContext.set(authUser);

        return authUser;
    }
}
