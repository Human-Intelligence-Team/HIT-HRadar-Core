package org.hit.hradar.domain.user.query.service;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OneTimeTempPasswordStoreService {

  private final StringRedisTemplate stringRedisTemplate;

  private static final Duration TTL = Duration.ofMinutes(10);

  public void save(Long applicationId, String tempPassword) {
    stringRedisTemplate.opsForValue().set(key(applicationId), tempPassword, TTL);
  }

  /** 1회 조회: 읽고 즉시 삭제 */
  public String pop(Long applicationId) {
    String key = key(applicationId);
    String value = stringRedisTemplate.opsForValue().get(key);
    if (value != null) {
      stringRedisTemplate.delete(key);
    }
    return value;
  }

  private String key(Long applicationId) {
    return "tempPw:companyApp:" + applicationId;
  }
}
