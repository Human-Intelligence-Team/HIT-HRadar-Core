package org.hit.authentication.common.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender mailSender;

  public void sendPasswordResetMail(String to, String token) {
    // 프론트 비번 재설정 페이지 URL로 변경
    String link = "http://localhost:5173/reset-password?token=" + token;

    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
      helper.setTo(to);
      helper.setSubject("[HIT-HRadar] 비밀번호 재설정 안내");
      helper.setText("""
          <p>비밀번호 재설정을 요청하셨습니다.</p>
          <p><a href="%s">비밀번호 재설정</a></p>
          <p>본 링크는 30분간 유효합니다.</p>
          <p>요청하지 않으셨다면 이 메일을 무시하세요.</p>
          """.formatted(link), true);

      mailSender.send(message);
    } catch (Exception e) {
      throw new IllegalStateException("이메일 발송 실패", e);
    }
  }
}
