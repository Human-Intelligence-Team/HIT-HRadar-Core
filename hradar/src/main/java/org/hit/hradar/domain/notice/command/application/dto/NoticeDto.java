package org.hit.hradar.domain.notice.command.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class NoticeDto {

    private final Long categoryId;
    private final String title;
    private final String content;
    private final Long companyId;
    private List<Long> deletedAttachmentIds;
    private List<String> attachmentStoredNames;

    public NoticeDto(Long categoryId, String title, String content, Long companyId,
            List<Long> deletedAttachmentIds, List<String> attachmentStoredNames) {
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.companyId = companyId;
        this.deletedAttachmentIds = deletedAttachmentIds;
        this.attachmentStoredNames = attachmentStoredNames;
    }
}
