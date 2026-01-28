package org.hit.authentication.auth.command.application.service;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthRedisService {

  private final long REFRESH_TOKEN_EXPIRY = 14; // 2주
  private final long PASSWORD_RESET_TOKEN_EXPIRY_MINUTES = 15; // 15분


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

  /* ========== Password Reset Token ========== */

  public void savePasswordResetToken(String resetToken, Long userId) {
    String key = "pwdReset:" + resetToken;
    redisTemplate.opsForValue().set(key, userId, PASSWORD_RESET_TOKEN_EXPIRY_MINUTES, TimeUnit.MINUTES);
  }

  public Long findUserIdByPasswordResetToken(String resetToken) {
    String key = "pwdReset:" + resetToken;
    Object v = redisTemplate.opsForValue().get(key);
    if (v == null) return null;
    return (v instanceof Long l) ? l : Long.valueOf(v.toString());
  }

  public void deletePasswordResetToken(String resetToken) {
    String key = "pwdReset:" + resetToken;
    redisTemplate.delete(key);
  }
}
