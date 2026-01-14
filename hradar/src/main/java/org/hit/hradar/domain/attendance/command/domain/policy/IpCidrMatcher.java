package org.hit.hradar.domain.attendance.command.domain.policy;

import java.net.InetAddress;


//CIDR 표기법(IP 대역)에 client IP가 포함되는지 판단하는 유틸리티 클래스
//CIDR : 192.168.0.0/24  === IP   : 192.168.0.15
//계산 전담 클래스
public final class IpCidrMatcher {

  //유틸 클래스이므로 인스턴스 생성 방지
  private IpCidrMatcher() {}

  //clientIp가 cidr 범위 안에 포함되는지 확인
  public static boolean isInRange(String cidr, String clientIp) {
    try {
      // cidr = "192.168.0.0/24"
      // parts[0] = "192.168.0.0"
      // parts[1] = "24"
      String[] parts = cidr.split("/");
      // 기준 네트워크 주소 (192.168.0.0)
      InetAddress base = InetAddress.getByName(parts[0]);

      // 네트워크 프리픽스 길이 (/24)
      int prefix = Integer.parseInt(parts[1]);

      //prefix = 24 → 앞의 24비트는 네트워크, 뒤 8비트는 호스트
      int mask = -((1 << (32 - prefix)) - 1);
      // 기준 네트워크 IP를 int로 변환
      int baseInt = toInt(base.getAddress());

      // 클라이언트 IP를 int로 변환
      int clientInt = toInt(InetAddress.getByName(clientIp).getAddress());

      //네트워크 부분만 비교
      return (baseInt & mask) == (clientInt & mask);

    } catch (Exception e) {
      // IP 형식 오류, 파싱 오류 → 범위 밖으로 처리
      return false;
    }
  }

  //byte[4] 형태의 IPv4 주소를 int로 변환
  //192.168.0.1
  //→ 11000000 10101000 00000000 00000001
  private static int toInt(byte[] b) {
    return ((b[0] & 0xFF) << 24)
        | ((b[1] & 0xFF) << 16)
        | ((b[2] & 0xFF) << 8)
        | (b[3] & 0xFF);
  }
}