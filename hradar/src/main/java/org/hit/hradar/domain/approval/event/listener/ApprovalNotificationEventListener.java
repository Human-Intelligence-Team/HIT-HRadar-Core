package org.hit.hradar.domain.approval.event.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocument;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLine;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLineStep;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalStepStatus;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalDocumentJpaRepository;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalLineJpaRepository;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalLineStepJpaRepository;
import org.hit.hradar.domain.approval.command.infrastructure.ApprovalReferenceJpaRepository;
import org.hit.hradar.domain.approval.event.ApprovalEvent;
import org.hit.hradar.domain.approval.event.ApprovalEventType;
import org.hit.hradar.domain.user.query.mapper.UserAccountQueryMapper;
import org.hit.hradar.global.notification.HrNotificationProducer;
import org.hit.hradar.global.notification.NotificationDTO;
import org.hit.hradar.global.notification.NotificationType;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApprovalNotificationEventListener {

    private final ApprovalDocumentJpaRepository approvalDocumentRepository;
    private final ApprovalLineJpaRepository approvalLineRepository;
    private final ApprovalLineStepJpaRepository approvalLineStepRepository;
    private final ApprovalReferenceJpaRepository approvalReferenceRepository;
    private final UserAccountQueryMapper userAccountQueryMapper;
    private final HrNotificationProducer hrNotificationProducer;

    @EventListener
    @Transactional(readOnly = true)
    public void handleApprovalEvent(ApprovalEvent event) {
        log.info("Handling approval event: type={}, docId={}", event.getType(), event.getDocId());

        ApprovalDocument doc = approvalDocumentRepository.findById(event.getDocId()).orElse(null);
        if (doc == null) return;

        Long docId = doc.getDocId();
        String title = doc.getTitle();
        String linkUrl = "/approval/" + docId;

        switch (event.getType()) {
            case SUBMITTED:
                // 1. 첫 결재자에게 알림
                notifyNextApprover(docId, title, linkUrl, event.getActorId());
                // 2. 참조자들에게 알림
                notifyReferences(docId, title, linkUrl, event.getActorId());
                break;

            case APPROVED:
                // 1. 다음 결재자에게 알림 (문서가 여전히 IN_PROGRESS인 경우)
                if (doc.isInProgress()) {
                    notifyNextApprover(docId, title, linkUrl, event.getActorId());
                } else if (doc.isApproved()) {
                    // 2. 최종 승인 시 기안자에게 알림
                    notifyWriter(doc.getWriterId(), "결재 승인 완료", "기안하신 문서가 최종 승인되었습니다: " + title, linkUrl, event.getActorId());
                }
                break;

            case REJECTED:
                // 기안자에게 반려 알림
                notifyWriter(doc.getWriterId(), "결재 반려", "기안하신 문서가 반려되었습니다: " + title, linkUrl, event.getActorId());
                break;
        }
    }

    private void notifyNextApprover(Long docId, String title, String linkUrl, Long actorId) {
        approvalLineRepository.findByDocId(docId).ifPresent(line -> {
            approvalLineStepRepository.findFirstByLineIdAndApprovalStepStatusOrderByStepOrderAsc(
                line.getLineId(), ApprovalStepStatus.PENDING
            ).ifPresent(step -> {
                hrNotificationProducer.sendNotification(new NotificationDTO(
                    NotificationType.REPORT_CREATED, // Use appropriate type or generic
                    step.getApproverId(), // Already accountId
                    "결재 요청",
                    "새로운 결재 요청이 도착했습니다: " + title,
                    linkUrl,
                    actorId
                ));
            });
        });
    }

    private void notifyReferences(Long docId, String title, String linkUrl, Long actorId) {
        approvalReferenceRepository.findAllByDocId(docId).forEach(ref -> {
            hrNotificationProducer.sendNotification(new NotificationDTO(
                NotificationType.REPORT_CREATED,
                ref.getRefEmpId(), // Already accountId
                "결재 참조",
                "결재 문서의 참조자로 지정되었습니다: " + title,
                linkUrl,
                actorId
            ));
        });
    }

    private void notifyWriter(Long writerEmpId, String notiTitle, String message, String linkUrl, Long actorId) {
        Long writerAccId = userAccountQueryMapper.findAccIdByEmpId(writerEmpId);
        if (writerAccId != null) {
            hrNotificationProducer.sendNotification(new NotificationDTO(
                NotificationType.REPORT_CREATED,
                writerAccId,
                notiTitle,
                message,
                linkUrl,
                actorId
            ));
        }
    }
}
