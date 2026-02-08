package org.hit.hradar.domain.leave.command.application.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalPayload;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalDocumentJpaRepository;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalPayloadJpaRepository;
import org.hit.hradar.domain.approval.event.ApprovalEvent;
import org.hit.hradar.domain.approval.event.ApprovalEventType;
import org.hit.hradar.domain.leave.command.domain.aggregate.LeaveGrant;
import org.hit.hradar.domain.leave.command.infrastructure.LeaveGrantJpaRepository;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeaveGrantApprovalEventListener {

    private final ApprovalDocumentJpaRepository documentRepository;
    private final ApprovalPayloadJpaRepository payloadRepository;
    private final LeaveGrantJpaRepository leaveGrantRepository;

    private final ObjectMapper objectMapper;


    @EventListener
    @Transactional
    public void onApprovalEvent(ApprovalEvent event) {
        log.info("ApprovalEvent received: docId={}, type={}", event.getDocId(), event.getType());
        
        if (event.getType() != ApprovalEventType.APPROVED) {
            log.info("Ignoring event type: {}", event.getType());
            return;
        }

        documentRepository.findById(event.getDocId()).ifPresentOrElse(doc -> {
            log.info("Found document for docId: {}. Type: {}, Status: {}", doc.getDocId(), doc.getDocType(), doc.getStatus());
            
            // Check if it's a LEAVE_GRANT document. 
            // We rely on the event type being APPROVED.
            if ("LEAVE_GRANT".equals(doc.getDocType())) {
                log.info("Starting LEAVE_GRANT processing for docId: {}", doc.getDocId());
                handleLeaveGrantApproval(doc.getDocId());
            } else {
                log.info("Document type is not LEAVE_GRANT: {}", doc.getDocType());
            }
        }, () -> {
            log.error("Document NOT FOUND for docId: {}", event.getDocId());
        });

    }

    private void handleLeaveGrantApproval(Long docId) {
        log.info("Fetching payload for docId: {}", docId);

        payloadRepository.findByDocId(docId).ifPresentOrElse(payloadEntity -> {
            log.info("Payload entity found for docId: {}. Length: {}", docId, payloadEntity.getPayload().length());
            try {
                JsonNode payload = objectMapper.readTree(payloadEntity.getPayload());
                log.info("Parsed JSON payload: {}", payload.toString());

                if (!payload.has("targetEmployeeId") || !payload.has("year") || !payload.has("days")) {
                    log.error("Missing required fields (targetEmployeeId, year, days) in payload: {}", payload.toString());
                    return;
                }

                Long targetEmpId = payload.get("targetEmployeeId").asLong();
                int year = payload.get("year").asInt();
                double days = payload.get("days").asDouble();

                log.info("Processing Leave Grant for targetEmpId: {}, Year: {}, Days: {}", targetEmpId, year, days);

                LocalDate grantedDate = LocalDate.now();
                LocalDate expireDate = grantedDate.withMonth(12).withDayOfMonth(31);

                LeaveGrant grant = LeaveGrant.create(
                    targetEmpId,
                    year,
                    days,
                    grantedDate,
                    expireDate
                );

                log.info("Saving new LeaveGrant for empId: {}, year: {}, days: {}", targetEmpId, year, days);
                leaveGrantRepository.save(grant);
                log.info("Successfully persisted LeaveGrant for docId: {}", docId);

            } catch (Exception e) {
                log.error("EXCEPTIONAL failure processing LEAVE_GRANT for docId: {}", docId, e);
            }
        }, () -> {
            log.error("Payload NOT FOUND for docId: {}", docId);
        });
    }
}
