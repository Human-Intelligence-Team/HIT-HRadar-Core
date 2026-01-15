package org.hit.authentication.auth.command.application.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.hit.authentication.auth.AuthErrorCode;
import org.hit.authentication.common.exception.BusinessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthRedisService {

  private final long REFRESH_TOKEN_EXPIRY = 14; // 2주
  private final long RESET_TOKEN_VALIDITY_MINUTES = 15; // 비밀번호 재설정 토큰 유효시간

  private final RedisTemplate<String, Object> redisTemplate;

  /* ========== Refresh Token ========== */

  public void saveRefreshToken(Long userId, String refreshToken) {
    String key = "refreshToken:" + userId;

    redisTemplate.opsForValue()
        .set(
            key, refreshToken, REFRESH_TOKEN_EXPIRY, TimeUnit.DAYS);
  }

  public Boolean existRefreshTokenByUserId(Long userId) {
    String key = "refreshToken:" + userId;

    return redisTemplate.hasKey(key);
  }

  public String findKeyByUserId(Long userId) {
    String key = "refreshToken:" + userId;

    return redisTemplate.opsForValue()
        .get(key)
        .toString();
  }

  public void deleteRefreshTokenByUserId(Long userId) {
    String key = "refreshToken:" + userId;

    if (redisTemplate.hasKey(key)) {
      redisTemplate.delete(key);
    }
  }
}
