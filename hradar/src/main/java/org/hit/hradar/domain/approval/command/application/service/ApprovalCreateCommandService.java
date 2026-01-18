package org.hit.hradar.domain.approval.command.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocument;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocumentType;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLine;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLineStep;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalDocumentJpaRepository;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalLineJpaRepository;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalLineStepJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalCreateCommandService {

  private final ApprovalDocumentJpaRepository approvalDocumentJpaRepository;
  private final ApprovalLineJpaRepository approvalLineJpaRepository;
  private final ApprovalLineStepJpaRepository approvalLineStepJpaRepository;

  //결재 문서 임시 생성(각 업무 서비스에서 호출)
  //문서/결재선/결재단계 생성
  public Long createDraft(
      Long writerId,
      ApprovalDocumentType docType,
      Long payloadId,
      String title,
      String content,
      List<Long> approverIds
  ) {
    //문서 생성
    ApprovalDocument doc =
        ApprovalDocument.createDraft(
            writerId,
            docType,
            payloadId,
            title,
            content
        );
    approvalDocumentJpaRepository.save(doc);

    //결재선 생성
    ApprovalLine line = ApprovalLine.create(doc.getDocId());
    approvalLineJpaRepository.save(line);

    //결재 단계 생성
    for (int i = 0; i < approverIds.size(); i++) {

      ApprovalLineStep step =
          ApprovalLineStep.create(
              line.getLineId(),
              i + 1,
              approverIds.get(i),
              i == 0 // 첫 단계만 PENDING
          );
      approvalLineStepJpaRepository.save(step);
    }
    return doc.getDocId();
  }
}
