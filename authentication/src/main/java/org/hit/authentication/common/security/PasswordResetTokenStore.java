package org.hit.authentication.common.security;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordResetTokenStore {

  private final RedisTemplate<String, Object> redisTemplate;

  public void savePasswordResetToken(String resetToken, Long accountId, long expiryMinutes) {
    redisTemplate.opsForValue().set(key(resetToken), accountId, expiryMinutes, TimeUnit.MINUTES);
  }

  public Long findAccountIdByPasswordResetToken(String resetToken) {
    Object v = redisTemplate.opsForValue().get(key(resetToken));
    if (v == null) return null;
    if (v instanceof Long l) return l;
    return Long.valueOf(v.toString());
  }

  public void deletePasswordResetToken(String resetToken) {
    redisTemplate.delete(key(resetToken));
  }

  private String key(String resetToken) {
    return "pwdReset:" + resetToken;
  }
}
