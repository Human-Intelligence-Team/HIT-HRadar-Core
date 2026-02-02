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
import org.hit.hradar.global.exception.BusinessException;
import org.hit.hradar.global.file.FileType;
import org.hit.hradar.global.file.FileUploadService;
import org.hit.hradar.global.file.StoredFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoticeCommandServiceTest {

        @InjectMocks
        private NoticeCommandService noticeCommandService;

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

        /* ===================== 이미지 업로드 ===================== */

        @Test
        void uploadImage_success() {
                MultipartFile file = mock(MultipartFile.class);
                StoredFile stored = new StoredFile("/files/a.png", "a.png");

                when(fileUploadService.upload(file, FileType.IMAGE)).thenReturn(stored);

                String url = noticeCommandService.uploadImage(1L, file);

                assertThat(url).isEqualTo("/files/a.png");
                verify(imageRepository).save(any(NoticeImage.class));
        }

        /* ===================== 공지 생성 ===================== */

        @Test
        void create_notice_success() {
                NoticeCategory category = mock(NoticeCategory.class);
                Notice notice = mock(Notice.class);

                NoticeDto dto = new NoticeDto(
                                1L,
                                "title",
                                "<img src=\"/files/a.png\">",
                                1L,
                                null);

                when(categoryRepository.findByIdAndCompanyIdAndIsDeletedNot(1L, 1L, 'Y'))
                                .thenReturn(Optional.of(category));

                when(noticeRepository.save(any(Notice.class))).thenReturn(notice);
                when(notice.getId()).thenReturn(10L);

                NoticeImage image = mock(NoticeImage.class);
                when(image.getUrl()).thenReturn("/files/a.png");

                when(imageRepository.findAllByCompanyIdAndUsedFalse(1L))
                                .thenReturn(List.of(image));

                Long id = noticeCommandService.create(dto, null);

                assertThat(id).isEqualTo(10L);
                verify(image).attachToNotice(10L);
        }

        @Test
        void create_fail_categoryNotFound() {
                NoticeDto dto = new NoticeDto(1L, "t", "c", 1L, null);

                when(categoryRepository.findByIdAndCompanyIdAndIsDeletedNot(1L, 1L, 'Y'))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> noticeCommandService.create(dto, null))
                                .isInstanceOf(BusinessException.class)
                                .hasMessageContaining("카테고리가 존재하지 않습니다.");
        }

        /* ===================== 이미지 삭제 ===================== */

        @Test
        void deleteImage_success() {
                NoticeImage image = mock(NoticeImage.class);

                when(imageRepository.findByCompanyIdAndUrlAndUsedFalse(1L, "/files/a.png"))
                                .thenReturn(Optional.of(image));

                when(image.getStoredName()).thenReturn("a.png");

                noticeCommandService.deleteImage(1L, "/files/a.png");

                verify(fileUploadService).delete("a.png");
                verify(imageRepository).delete(image);
        }

        /* ===================== 공지 수정 (이미지 diff) ===================== */

        @Test
        void updateNotice_removeUnusedImage() {
                Notice notice = mock(Notice.class);
                NoticeCategory category = mock(NoticeCategory.class);

                NoticeImage oldImage = mock(NoticeImage.class);
                when(oldImage.getUrl()).thenReturn("/files/old.png");
                when(oldImage.getStoredName()).thenReturn("old.png");

                when(noticeRepository.findById(10L))
                                .thenReturn(Optional.of(notice));

                when(categoryRepository.findByIdAndCompanyIdAndIsDeletedNot(1L, 1L, 'Y'))
                                .thenReturn(Optional.of(category));

                when(imageRepository.findAllByNoticeId(10L))
                                .thenReturn(List.of(oldImage));

                NoticeDto dto = new NoticeDto(
                                1L,
                                "new title",
                                "<p>no image</p>",
                                1L,
                                null);

                noticeCommandService.updateNotice(10L, dto, null);

                verify(fileUploadService).delete("old.png");
                verify(imageRepository).delete(oldImage);
                verify(notice).update(category, "new title", "<p>no image</p>");
        }

        /* ===================== 공지 삭제 ===================== */

        @Test
        void deleteNotice_success() {
                Notice notice = mock(Notice.class);
                NoticeImage image = mock(NoticeImage.class);
                NoticeAttachment attachment = mock(NoticeAttachment.class);

                when(noticeRepository.findByIdAndCompanyId(10L, 1L))
                                .thenReturn(Optional.of(notice));

                when(imageRepository.findAllByNoticeId(10L))
                                .thenReturn(List.of(image));

                when(attachmentRepository.findAllByNoticeId(10L))
                                .thenReturn(List.of(attachment));

                when(image.getStoredName()).thenReturn("img.png");
                when(attachment.getStoredName()).thenReturn("file.pdf");

                noticeCommandService.deleteNotice(10L, 1L);

                verify(notice).delete();
                verify(fileUploadService).delete("img.png");
                verify(fileUploadService).delete("file.pdf");
                verify(imageRepository).delete(image);
                verify(attachmentRepository).delete(attachment);
        }
}
