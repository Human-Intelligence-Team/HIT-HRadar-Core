package org.hit.hradar.global.util;

import java.security.SecureRandom;

public final class RandomGenerator {

  private static final SecureRandom RANDOM = new SecureRandom();

  // 헷갈리기 쉬운 문자 제거 (0,O / 1,l,I)
  private static final char[] CODE_CHARS =
      "ABCDEFGHJKLMNPQRSTUVWXYZ23456789".toCharArray();

  // 임시 비밀번호: 대/소문자 + 숫자 + 특수문자(제한된 세트)
  private static final char[] PW_UPPER = "ABCDEFGHJKLMNPQRSTUVWXYZ".toCharArray();
  private static final char[] PW_LOWER = "abcdefghjkmnpqrstuvwxyz".toCharArray();
  private static final char[] PW_DIGIT = "23456789".toCharArray();
  private static final char[] PW_SYMBOL = "!@#$%^&*".toCharArray();

  private RandomGenerator() {}

  /**
   * 회사코드 생성 (prefix 없음)
   * 예: 8F3KQ2
   */
  public static String generateCompanyCode(int len) {
    if (len < 4) throw new IllegalArgumentException("len must be >= 4");

    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      sb.append(CODE_CHARS[RANDOM.nextInt(CODE_CHARS.length)]);
    }
    return sb.toString();
  }

  /**
   * 임시 비밀번호 생성
   * - 구성요건: 대문자 1 + 소문자 1 + 숫자 1 + 특수문자 1 포함
   */
  public static String generateTempPassword(int length) {
    if (length < 8) throw new IllegalArgumentException("password length must be >= 8");

    // 필수 4종 1개씩 보장
    char[] pw = new char[length];
    int idx = 0;
    pw[idx++] = PW_UPPER[RANDOM.nextInt(PW_UPPER.length)];
    pw[idx++] = PW_LOWER[RANDOM.nextInt(PW_LOWER.length)];
    pw[idx++] = PW_DIGIT[RANDOM.nextInt(PW_DIGIT.length)];
    pw[idx++] = PW_SYMBOL[RANDOM.nextInt(PW_SYMBOL.length)];

    // 나머지는 풀(pool)에서 랜덤
    char[] pool = concat(PW_UPPER, PW_LOWER, PW_DIGIT, PW_SYMBOL);
    while (idx < length) {
      pw[idx++] = pool[RANDOM.nextInt(pool.length)];
    }

    // 섞기 (Fisher-Yates)
    for (int i = pw.length - 1; i > 0; i--) {
      int j = RANDOM.nextInt(i + 1);
      char tmp = pw[i];
      pw[i] = pw[j];
      pw[j] = tmp;
    }

    return new String(pw);
  }

  private static char[] concat(char[]... arrays) {
    int total = 0;
    for (char[] a : arrays) total += a.length;

    char[] result = new char[total];
    int pos = 0;
    for (char[] a : arrays) {
      System.arraycopy(a, 0, result, pos, a.length);
      pos += a.length;
    }
    return result;
  }
}
