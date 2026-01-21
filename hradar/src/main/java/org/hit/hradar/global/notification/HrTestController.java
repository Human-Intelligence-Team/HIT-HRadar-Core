package org.hit.hradar.global.notification;

import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HrTestController {

    private final HrNotificationProducer producer;

    public HrTestController(HrNotificationProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/test/notify")
    public String notifyTest() {
        NotificationDTO notificationDTO = new NotificationDTO(NotificationType.REPORT_CREATED, 1L, "알림 테스트", "알림 메시지 테스트입니다.", "/notice");
        producer.sendNotification(notificationDTO);
        return "sent";
    }

    @GetMapping("/test/notify2")
    public String notifyTest2(@CurrentUser AuthUser authUser) {

      Long empId = authUser.employeeId();
      Long userId = authUser.userId();
      Long companyId = authUser.companyId();
      String role = authUser.role();

      System.out.println("empId: " + empId);
      System.out.println("userId: " + userId);
      System.out.println("companyId: " + companyId);
      System.out.println("role: " + role);

        NotificationDTO notificationDTO = new NotificationDTO(NotificationType.REPORT_CREATED, authUser.userId(), "공지 알림 테스트", "공지 메시지 테스트입니다.", "/notice");
        producer.sendNotification(notificationDTO);
        return "sent";
    }

    @GetMapping("/test/notify3")
    public String notifyTest3() {
        NotificationDTO notificationDTO = new NotificationDTO(NotificationType.REPORT_CREATED, 2L, "테스트 알림", "테스트입니다.", "/notice");
        producer.sendNotification(notificationDTO);
        return "sent";
    }
}