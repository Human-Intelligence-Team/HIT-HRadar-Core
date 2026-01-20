package org.hit.hradar.domain.approval.command.application.service.provider;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.command.application.dto.request.ApprovalCreateRequest;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocument;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLine;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLineStep;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalReference;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalDocumentJpaRepository;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalLineJpaRepository;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalLineStepJpaRepository;
import org.hit.hradar.domain.approval.command.domain.infrastructure.ApprovalReferenceJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalProviderService {

  private final ApprovalDocumentJpaRepository approvalDocumentJpaRepository;
  private final ApprovalLineJpaRepository approvalLineJpaRepository;
  private final ApprovalLineStepJpaRepository approvalLineStepJpaRepository;
  private final ApprovalReferenceJpaRepository approvalReferenceJpaRepository;

  //결재 문서 임시 생성(각 업무 서비스에서 호출)
  //문서/결재선/결재단계 생성
  public Long createDraft(
      Long writerId,
      ApprovalCreateRequest request
  ) {
    //문서 생성
    ApprovalDocument doc =
        ApprovalDocument.createDraft(
            writerId,
            request.getDocType(),
            request.getTitle(),
            request.getContent()
        );
    approvalDocumentJpaRepository.save(doc);

    //결재선 생성
    ApprovalLine line = ApprovalLine.create(doc.getDocId());
    approvalLineJpaRepository.save(line);

    //결재 단계 생성
    for (int i = 0; i < request.getApproverIds().size(); i++) {
      approvalLineStepJpaRepository.save(
          ApprovalLineStep.create(
              line.getLineId(),
              i + 1,
              request.getApproverIds().get(i),    //결재자 생성
              i == 0 // 첫 단계만 PENDING
          )
      );
    }

    if (request.getReferenceIds() != null
        && !request.getReferenceIds().isEmpty()) {
      approvalReferenceJpaRepository.saveAll(
          ApprovalReference.createAll(
              doc.getDocId(),
              request.getReferenceIds()
          )
      );
    }

    //docid 반환
    return doc.getDocId();
  }

}
