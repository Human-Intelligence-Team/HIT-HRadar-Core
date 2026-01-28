package org.hit.hradar.domain.notice.command.application.service;

import org.hit.hradar.domain.notice.command.application.dto.NoticeDto;
import org.hit.hradar.domain.notice.command.domain.aggregate.Notice;
import org.hit.hradar.domain.notice.command.domain.aggregate.NoticeAttachment;
import org.hit.hradar.domain.notice.command.domain.aggregate.NoticeCategory;
import org.hit.hradar.domain.notice.command.domain.aggregate.NoticeImage;
import org.hit.hradar.domain.notice.command.domain.repository.NoticeAttachmentRepository;
import org.hit.hradar.domain.notice.command.domain.repository.NoticeCategoryRepository;
import org.hit.hradar.domain.notice.command.domain.repository.NoticeImageRepository;
import org.hit.hradar.domain.notice.command.domain.repository.NoticeRepository;
import org.hit.hradar.global.file.FileType;
import org.hit.hradar.global.file.FileUploadService;
import org.hit.hradar.global.file.StoredFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoticeCommandServiceTest {

    @InjectMocks
    private NoticeCommandService service;

    @Mock
    private NoticeRepository noticeRepository;

    @Mock
    private NoticeCategoryRepository categoryRepository;

    @Mock
    private NoticeImageRepository imageRepository;

    @Mock
    private NoticeAttachmentRepository attachmentRepository;

    @Mock
    private FileUploadService fileUploadService;

    /**
     * 본문 이미지 업로드 성공
     */
    @Test
    void uploadImage_success() {
        // given
        Long companyId = 1L;
        MultipartFile image = mock(MultipartFile.class);

        when(image.getOriginalFilename()).thenReturn("test.png");

        StoredFile stored = new StoredFile(
                "/files/uuid.png",
                "uuid.png"
        );

        when(fileUploadService.upload(image, FileType.IMAGE))
                .thenReturn(stored);

        // when
        String url = service.uploadImage(companyId, image);

        // then
        assertThat(url).isEqualTo("/files/uuid.png");

        ArgumentCaptor<NoticeImage> captor =
                ArgumentCaptor.forClass(NoticeImage.class);

        verify(imageRepository).save(captor.capture());

        NoticeImage saved = captor.getValue();
        assertThat(saved.getCompanyId()).isEqualTo(companyId);
        assertThat(saved.getOriginalName()).isEqualTo("test.png");
        assertThat(saved.getUrl()).isEqualTo("/files/uuid.png");
    }

    /**
     * 공지 생성 성공 (첨부파일 있음)
     */
    @Test
    void create_success_withAttachments() {
        // given
        Long companyId = 1L;
        Long categoryId = 10L;

        NoticeDto req = mock(NoticeDto.class);
        when(req.getCompanyId()).thenReturn(companyId);
        when(req.getCategoryId()).thenReturn(categoryId);
        when(req.getTitle()).thenReturn("공지 제목");
        when(req.getContent()).thenReturn("본문 <img src=\"/files/img1.png\" />");

        NoticeCategory category = NoticeCategory.create(companyId, "공지");
        when(categoryRepository.findByIdAndCompanyIdAndIsDeletedNot(
                categoryId, companyId, 'Y'
        )).thenReturn(Optional.of(category));

        Notice notice = mock(Notice.class);
        when(notice.getId()).thenReturn(100L);
        when(noticeRepository.save(any(Notice.class)))
                .thenReturn(notice);

        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("doc.pdf");

        StoredFile stored = new StoredFile(
                "/files/doc-uuid.pdf",
                "doc-uuid.pdf"
        );

        when(fileUploadService.upload(file, FileType.ATTACHMENT))
                .thenReturn(stored);

        NoticeImage image = mock(NoticeImage.class);
        when(image.getUrl()).thenReturn("/files/img1.png");

        when(imageRepository.findAll()).thenReturn(List.of(image));

        // when
        Long noticeId = service.create(req, List.of(file));

        // then
        assertThat(noticeId).isEqualTo(100L);

        verify(noticeRepository).save(any(Notice.class));
        verify(attachmentRepository).save(any(NoticeAttachment.class));
        verify(fileUploadService).upload(file, FileType.ATTACHMENT);
        verify(image).markUsed();
    }

    /**
     * 공지 생성 성공 (첨부파일 없음)
     */
    @Test
    void create_success_withoutAttachments() {
        // given
        Long companyId = 1L;
        Long categoryId = 10L;

        NoticeDto req = mock(NoticeDto.class);
        when(req.getCompanyId()).thenReturn(companyId);
        when(req.getCategoryId()).thenReturn(categoryId);
        when(req.getTitle()).thenReturn("공지 제목");
        when(req.getContent()).thenReturn("본문");

        NoticeCategory category = NoticeCategory.create(companyId, "공지");
        when(categoryRepository.findByIdAndCompanyIdAndIsDeletedNot(
                categoryId, companyId, 'Y'
        )).thenReturn(Optional.of(category));

        Notice notice = mock(Notice.class);
        when(notice.getId()).thenReturn(200L);
        when(noticeRepository.save(any(Notice.class)))
                .thenReturn(notice);

        when(imageRepository.findAll()).thenReturn(List.of());

        // when
        Long noticeId = service.create(req, null);

        // then
        assertThat(noticeId).isEqualTo(200L);

        verify(attachmentRepository, never()).save(any());
    }
}
