package org.hit.hradar.global.notification;

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
        producer.sendNotification(1L, "역량 리포트가 생성되었습니다.");
        return "sent";
    }

    @GetMapping("/test/notify2")
    public String notifyTest2() {
        producer.sendNotification(1L, "테스트");
        return "sent";
    }

    @GetMapping("/test/notify3")
    public String notifyTest3() {
        producer.sendNotification(2L, "역량 리포트가 생성되었습니다.");
        return "sent";
    }
}