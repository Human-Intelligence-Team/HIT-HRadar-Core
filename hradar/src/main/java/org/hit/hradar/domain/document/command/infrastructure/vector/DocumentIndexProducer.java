package org.hit.hradar.domain.document.command.infrastructure.vector;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.document.command.domain.application.dto.request.DocumentIndexEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentIndexProducer {

    private final KafkaTemplate<String, DocumentIndexEvent> kafkaTemplate;

    private static final String TOPIC = "document.index";

    public void send(DocumentIndexEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.getCompanyId().toString(),
                event
        );
    }
}

