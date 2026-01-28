package org.hit.authentication.auth.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.authentication.auth.command.application.service.AuthTokenRevokeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/internal/tokens")
public class InternalTokenController {

    private final AuthTokenRevokeService authTokenRevokeService;

    @PostMapping("/revoke")
    public ResponseEntity<Void> revoke(@RequestBody RevokeTokenRequest request) {
        authTokenRevokeService.revokeRefreshToken(request.userId());
        return ResponseEntity.ok().build();
    }

    public record RevokeTokenRequest(Long userId) {}
}
