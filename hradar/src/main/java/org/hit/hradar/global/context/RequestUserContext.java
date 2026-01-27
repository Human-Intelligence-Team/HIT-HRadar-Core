package org.hit.hradar.global.context;

import org.hit.hradar.global.dto.AuthUser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class RequestUserContext {

    private AuthUser authUser;

    public void set(AuthUser authUser) {
        this.authUser = authUser;
    }

    public AuthUser get() {
        return authUser;
    }

    public Long userId() {
        return authUser != null ? authUser.userId() : null;
    }
}
