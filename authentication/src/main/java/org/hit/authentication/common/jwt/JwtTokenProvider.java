package org.hit.authentication.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import java.util.Date;
import javax.crypto.SecretKey;

import lombok.RequiredArgsConstructor;
import org.hit.authentication.auth.command.application.dto.TokenDTO;
import org.hit.authentication.auth.command.domain.aggregate.Role;
import org.hit.authentication.common.exception.InvalidJwtTokenException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(
                jwtProperties.getSecretKey().getBytes()
        );
    }

    /**
     * Access Token 생성
     */
    public String createAccessToken(TokenDTO tokenDTO) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getExpiration());

        return Jwts.builder()
                .subject(String.valueOf(tokenDTO.getUserId()))
                .claim("companyId", String.valueOf(tokenDTO.getCompanyId()))
                .claim("role", tokenDTO.getRole().name())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    /**
     * Refresh Token 생성
     */
    public String createRefreshToken(TokenDTO tokenDTO) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getRefreshExpiration());

        return Jwts.builder()
                .subject(String.valueOf(tokenDTO.getUserId()))
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    /**
     * 토큰 검증
     */
    public void validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
        } catch (SecurityException | MalformedJwtException e) {
            throw new InvalidJwtTokenException("유효하지 않은 토큰입니다.", e);
        } catch (ExpiredJwtException e) {
            throw new InvalidJwtTokenException("만료된 토큰입니다.", e);
        } catch (UnsupportedJwtException e) {
            throw new InvalidJwtTokenException("지원하지 않는 토큰입니다.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidJwtTokenException("토큰 값이 비어 있습니다.", e);
        }
    }

    /**
     * userId 추출
     */
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.parseLong(claims.getSubject());
    }

    /**
     * Role 추출
     */
    public Role getRoleFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Role.valueOf(claims.get("role", String.class));
    }

    /**
     * 남은 만료 시간(ms)
     */
    public long getRemainingTime(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getExpiration().getTime() - System.currentTimeMillis();
    }

    /**
     * Authorization 헤더에서 토큰 추출
     */
    public String resolveToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    public long getRefreshExpiration() {
        return jwtProperties.getRefreshExpiration();
    }
}
