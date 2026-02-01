package org.hit.hradar.domain.document.command.domain.application.event;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.document.command.domain.application.dto.request.DocumentIndexEvent;
import org.hit.hradar.domain.document.command.infrastructure.vector.DocumentIndexProducer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DocumentIndexEventHandler {

    private final DocumentIndexProducer producer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(DocumentIndexInternalEvent event) {

        producer.send(
                new DocumentIndexEvent(
                        UUID.randomUUID().toString(),
                        event.companyId(),
                        event.documentId(),
                        event.type(),
                        event.chunks()
                )
        );
    }
}