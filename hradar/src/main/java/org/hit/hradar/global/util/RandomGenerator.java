package org.hit.hradar.global.util;

import java.security.SecureRandom;

/**
 * 랜덤 문자열/숫자/코드 생성을 담당하는 공통 유틸 클래스
 */
public final class RandomGenerator {

  // 보안이 필요한 값 생성에 사용하는 SecureRandom 인스턴스
  private static final SecureRandom SECURE_RANDOM = new SecureRandom();

  // 영문 대문자 + 숫자 (회사코드/인증코드 등: 보기 쉬운 조합)
  private static final char[] ALPHANUM_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

  // 영문 대소문자 + 숫자 + 특수문자 (임시 비밀번호용)
  private static final char[] PASSWORD_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*"
      .toCharArray();

  // 인스턴스 생성 방지
  private RandomGenerator() {
  }

  /**
   * 지정된 길이의 영문 대문자 + 숫자 랜덤 문자열 생성
   *
   * @param length 생성할 문자열 길이
   * @return 랜덤 문자열
   */
  public static String randomUpperAlphaNumeric(int length) {
    StringBuilder sb = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      int idx = SECURE_RANDOM.nextInt(ALPHANUM_UPPER.length); // 0 ~ (문자셋 길이-1)
      sb.append(ALPHANUM_UPPER[idx]); // 랜덤 문자 1개 추가
    }

    return sb.toString();
  }

  /**
   * 회사 코드 생성 (prefix 없이 인증번호처럼 "문자+숫자"만 섞어서 생성)
   * - 예: A8Z1Q0B7
   *
   * @param length 전체 코드 길이
   * @return 회사 코드 문자열
   */
  public static String generateCompanyCode(int length) {
    // 회사코드는 대문자+숫자 조합만 사용 (하이픈/프리픽스 없음)
    return randomUpperAlphaNumeric(length);
  }

  /**
   * 임시 비밀번호 생성
   * - 대소문자, 숫자, 특수문자 혼합
   *
   * @param length 비밀번호 길이
   * @return 임시 비밀번호
   */
  public static String generateTempPassword(int length) {
    return "1234";
  }
}
