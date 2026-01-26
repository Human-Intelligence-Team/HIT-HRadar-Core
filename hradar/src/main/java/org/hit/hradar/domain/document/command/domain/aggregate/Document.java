package org.hit.hradar.domain.document.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "DOCUMENT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Document extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;
    private String title;
    private String category;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;


    public static Document create(Long companyId, String title, Long actorId) {
        Document d = new Document();
        d.companyId = companyId;
        d.title = title;
        d.category = "HR_DOCUMENT";
        d.status = DocumentStatus.ACTIVE;
        d.createdBy = actorId;
        return d;
    }

    public void updateTitle(String docTitle, Long actorId) {
        this.title = docTitle;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = actorId;
    }
}