package org.hit.hradar.domain.attendance.command.application.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocument;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalPayload;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalDocumentJpaRepository;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalPayloadJpaRepository;
import org.hit.hradar.domain.approval.event.ApprovalEvent;
import org.hit.hradar.domain.approval.event.ApprovalEventType;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkPlan;
import org.hit.hradar.domain.attendance.command.infrastructure.AttendanceWorkPlanJpaRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApprovalCompleteEventListener {

    private final ApprovalDocumentJpaRepository documentRepository;
    private final ApprovalPayloadJpaRepository payloadRepository;
    private final AttendanceWorkPlanJpaRepository workPlanRepository;
    private final org.hit.hradar.domain.approval.command.infrastructure.ApprovalDocumentTypeJpaRepository documentTypeRepository;
    private final ObjectMapper objectMapper;

    @EventListener
    @Transactional
    public void onApprovalEvent(ApprovalEvent event) {
        if (event.getType() != ApprovalEventType.APPROVED) {
            return;
        }

        Long docId = event.getDocId();
        log.info("Approval APPROVED event received for docId: {}", docId);

        ApprovalDocument doc = documentRepository.findById(docId).orElse(null);
        if (doc == null) {
            log.warn("Document not found for docId: {}", docId);
            return;
        }

        // Dynamic Lookup (Safe against accidental duplicates)
        org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocumentType docType = 
            documentTypeRepository.findFirstByCompanyIdAndDocTypeOrderByTypeIdDesc(doc.getCompanyId(), doc.getDocType())
            .orElse(null);

        if (docType == null) {
            log.warn("Document Type definition not found for: {}", doc.getDocType());
            return;
        }

        org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalAttendanceCategory category = docType.getAttendanceCategory();
        
        if (category == org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalAttendanceCategory.NONE) {
            return;
        }

        createAttendanceWorkPlan(doc, docType);
    }

    private void createAttendanceWorkPlan(ApprovalDocument doc, org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocumentType docType) {
        org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalAttendanceCategory category = docType.getAttendanceCategory();
        // Fetch Payload
        ApprovalPayload payloadEntity = payloadRepository.findByDocId(doc.getDocId()).orElse(null);
        if (payloadEntity == null) {
            log.warn("Payload not found for docId: {}", doc.getDocId());
            return;
        }

        try {
            JsonNode payload = objectMapper.readTree(payloadEntity.getPayload());
            String startDateStr = payload.has("startDate") ? payload.get("startDate").asText() : null;
            String endDateStr = payload.has("endDate") ? payload.get("endDate").asText() : null;

            // Prioritize overtime value from payload if present
            int overtMins = payload.has("overtimeMinutes") ? payload.get("overtimeMinutes").asInt() : docType.getOvertimeMinutes();

            if (startDateStr == null || endDateStr == null) {
                log.warn("Start/End date missing in payload for docId: {}", doc.getDocId());
                return;
            }

            LocalDate startDate = LocalDate.parse(startDateStr.substring(0, 10));
            LocalDate endDate = LocalDate.parse(endDateStr.substring(0, 10));

            String location = "OFFICE";
            String mappedWorkType = "OFFICE";

            switch (category) {
                case WORK_FIELD:
                    location = "FIELD";
                    mappedWorkType = "FIELD";
                    break;
                case WORK_TRIP:
                    location = "TRIP";
                    mappedWorkType = "TRIP";
                    break;
                case WORK_REMOTE:
                    location = "HOME";
                    mappedWorkType = "REMOTE";
                    break;
                case VACATION:
                    location = "NONE";
                    mappedWorkType = "VACATION";
                    break;
                case OVERTIME:
                    location = "OFFICE";
                    mappedWorkType = "WORK";
                    break;
                default:
                    return; // Should not happen given check above
            }

            AttendanceWorkPlan plan = AttendanceWorkPlan.create(
                doc.getWriterId(),
                doc.getDocId(),
                mappedWorkType,
                location,
                startDate.atTime(LocalTime.MIN),
                endDate.atTime(LocalTime.MAX),
                overtMins
            );
            plan.approve();
            
            workPlanRepository.save(plan);
            log.info("Created AttendanceWorkPlan (Dynamic) for docId: {}, category: {}, range: {} ~ {}", 
                     doc.getDocId(), category, startDate, endDate);

        } catch (Exception e) {
            log.error("Failed to process attendance work plan for docId: {}", doc.getDocId(), e);
        }
    }
}
