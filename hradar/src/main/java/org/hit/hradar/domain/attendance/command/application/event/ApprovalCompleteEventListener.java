package org.hit.hradar.domain.attendance.command.application.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocument;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalPayload;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalDocumentJpaRepository;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalPayloadJpaRepository;
import org.hit.hradar.domain.approval.event.ApprovalEvent;
import org.hit.hradar.domain.approval.event.ApprovalEventType;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceOvertime;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkPlan;
import org.hit.hradar.domain.attendance.command.infrastructure.AttendanceOvertimeJpaRepository;
import org.hit.hradar.domain.attendance.command.infrastructure.AttendanceWorkPlanJpaRepository;
import org.hit.hradar.domain.leave.command.application.service.LeaveCommandService;
import org.hit.hradar.global.notification.HrNotificationProducer;
import org.hit.hradar.global.notification.NotificationDTO;
import org.hit.hradar.global.notification.NotificationType;
import org.hit.hradar.domain.leave.command.domain.aggregate.EmpLeave;
import org.hit.hradar.domain.leave.command.infrastructure.EmpLeaveJpaRepository;
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
    private final AttendanceOvertimeJpaRepository overtimeRepository;
    private final org.hit.hradar.domain.approval.command.infrastructure.ApprovalDocumentTypeJpaRepository documentTypeRepository;
    private final LeaveCommandService leaveCommandService;
    private final EmpLeaveJpaRepository empLeaveRepository;
    private final ObjectMapper objectMapper;
    private final HrNotificationProducer hrNotificationProducer;

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

        if (docType.getDocType().equals("LEAVE")) {
            try {
                leaveCommandService.approveLeave(docId);
                log.info("Leave deduction processed for docId: {}", docId);
            } catch (Exception e) {
                log.error("Failed to process leave deduction for docId: {}", docId, e);
            }
        }

        org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalAttendanceCategory category = docType.getAttendanceCategory();
        
        if (category == org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalAttendanceCategory.NONE) {
            return;
        }

        createAttendanceWorkPlan(doc, docType, event.getActorId());
    }

    private void createAttendanceWorkPlan(ApprovalDocument doc, org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocumentType docType, Long actorId) {
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
                case LEAVE_SICK:
                    location = "NONE";
                    mappedWorkType = (category == org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalAttendanceCategory.LEAVE_SICK) ? "병가" : "VACATION";
                    // Try to get specific leave type name from EmpLeave
                    try {
                        EmpLeave leave = empLeaveRepository.findByDocId(doc.getDocId()).orElse(null);
                        if (leave != null) {
                            mappedWorkType = leave.getLeaveType(); // e.g., "병가", "반차"
                        }
                    } catch (Exception e) {
                        log.warn("Failed to find EmpLeave for docId: {}", doc.getDocId());
                    }
                    break;
                case OVERTIME:
                    location = "OFFICE";
                    mappedWorkType = "WORK";
                    break;
                default:
                    return; // Should not happen given check above
            }

            if (category == org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalAttendanceCategory.OVERTIME) {
                // 초과근무 전용 엔티티 생성
                for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                    AttendanceOvertime overtime = AttendanceOvertime.create(
                        doc.getWriterId(),
                        doc.getDocId(),
                        date,
                        overtMins
                    );
                    overtimeRepository.save(overtime);
                }
                log.info("Created AttendanceOvertime for docId: {}, range: {} ~ {}", 
                         doc.getDocId(), startDate, endDate);
            } else {
                // 기존 근무 계획 생성 (재택, 외근, 출장, 휴가 등)
                LocalDateTime planStart = startDate.atTime(LocalTime.MIN);
                LocalDateTime planEnd = endDate.atTime(LocalTime.MAX);

                // 반차 처리 (오전/오후)
                if (payload.has("leaveUnitType")) {
                    String unitType = payload.get("leaveUnitType").asText();
                    if ("AM_HALF".equals(unitType)) {
                        planStart = startDate.atTime(9, 0);
                        planEnd = startDate.atTime(14, 0);
                    } else if ("PM_HALF".equals(unitType)) {
                        planStart = startDate.atTime(14, 0);
                        planEnd = startDate.atTime(18, 0);
                    }
                }

                AttendanceWorkPlan plan = AttendanceWorkPlan.create(
                    doc.getWriterId(),
                    doc.getDocId(),
                    mappedWorkType,
                    location,
                    planStart,
                    planEnd,
                    overtMins
                );
                plan.approve();
                
                workPlanRepository.save(plan);
                log.info("Created AttendanceWorkPlan (Dynamic) for docId: {}, category: {}, range: {} ~ {}", 
                         doc.getDocId(), category, startDate, endDate);
            }

            // Notify real-time calendar updates
            hrNotificationProducer.sendNotification(new NotificationDTO(
                NotificationType.ATTENDANCE_CHANGED,
                doc.getWriterId(),
                "근무 계획 승인",
                "새로운 근무 계획이 승인되었습니다.",
                "/attendance/commute",
                actorId
            ));

        } catch (Exception e) {
            log.error("Failed to process attendance work plan for docId: {}", doc.getDocId(), e);
        }
    }
}
