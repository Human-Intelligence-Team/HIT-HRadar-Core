package org.hit.hradar.global.security.jwt;

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
import org.hit.hradar.domain.user.command.domain.aggregate.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  private final JwtProperties jwtProperties; // JWT 관련 DTO class
  private SecretKey secretKey;

  @Value("${jwt.expiration}")
  private long jwtExpiration;

  @Value("${jwt.refresh-expiration}")
  private long jwtRefreshExpiration;

  @PostConstruct
  public void init() {
    // 시크릿 키 초기화
    secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey()
        .getBytes());
  }

  /**
   * access token 생성
   *
   * @param loginId
   * @param role
   * @return
   */
  public String createAccessToken(String loginId, UserRole role) {
    Date now = new Date();
    Date expriyDate = new Date(now.getTime() + jwtProperties.getExpiration());
    return Jwts.builder()
        .subject(loginId)
        .claim("role", UserRole.valueOf(role.name()))
        .issuedAt(now)
        .expiration(expriyDate)
        .signWith(secretKey, Jwts.SIG.HS512)
        .compact();
  }

  /**
   * refresh token 생성
   *
   * @param loginId
   * @param role
   * @return
   */
  public String createRefreshToken(String loginId, UserRole role) {
    Date now = new Date();
    // 토큰 발급 시점 (현재 시간 기준)
    Date expriyDate = new Date(now.getTime() + jwtProperties.getRefreshExpiration());
    return Jwts.builder()
        .subject(loginId)
        .claim("role", UserRole.valueOf(role.name()))
        .issuedAt(now)
        .expiration(expriyDate)
        .signWith(secretKey, Jwts.SIG.HS512)
        .compact();
  }

  /**
   * 토큰 검증
   *
   * @param token
   * @return
   */
  public boolean validateToken(String token) {
    try {
      Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      throw new BadCredentialsException("Invalid JWT Token", e);
    } catch (ExpiredJwtException e) {
      throw new BadCredentialsException("Expired JWT Token", e);
    } catch (UnsupportedJwtException e) {
      throw new BadCredentialsException("Unsupported JWT Token", e);
    } catch (IllegalArgumentException e) {
      throw new BadCredentialsException("JWT Token claims empty", e);
    }
  }

  public String getLoginIdFromJWT(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();

    return claims.getSubject();
  }

  public long getRefreshExpiration() {
    return jwtRefreshExpiration;
  }

  public long getRemainingTime(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(secretKey) //문자열 key 반환
        .build()
        .parseClaimsJws(token)
        .getBody();

    Date expiration = claims.getExpiration();
    long now = System.currentTimeMillis();
    return expiration.getTime() - now;
  }

  public String resolveToken(String authorizationHeader) {
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    }
    return null;
  }

  public UserRole getUserRoleFromJWT(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();

    return UserRole.valueOf(claims.get("role", String.class));
  }
}
