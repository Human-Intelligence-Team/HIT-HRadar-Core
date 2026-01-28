package org.hit.authentication.auth.command.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthTokenRevokeService {

    private final AuthRedisService authRedisService;

    public void revokeRefreshToken(Long userId) {
        authRedisService.deleteRefreshTokenByUserId(userId);
    }
}
