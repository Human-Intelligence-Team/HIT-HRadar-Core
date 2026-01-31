package org.hit.hradar.domain.approval.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "approval_comment")
@Getter
public class ApprovalComment extends BaseTimeEntity {

  //댓글id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id", nullable = false)
  private Long commentId;

  //부모댓글id
  @Column(name ="parent_comment_id")
  private Long parentCommentId;

  //결재 문서id
  @Column(name = "approval_document_id", nullable = false)
  private Long approvalDocumentId;

  //작성자 사원id
  @Column(name = "writer_id", nullable = false)
  private Long writerId;

  //댓글 내용
  @Column(name = "content", nullable = false)
  private String content;

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

  public static ApprovalComment create(
      Long docId,
      Long writerId,
      String content,
      Long parentCommentId
  ) {
    ApprovalComment c = new ApprovalComment();
    c.approvalDocumentId = docId;
    c.writerId = writerId;
    c.content = content;
    c.parentCommentId = parentCommentId;
    return c;
  }
}
