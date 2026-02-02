package org.hit.hradar.domain.notice.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.notice.NoticeErrorCode;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

import static org.hit.hradar.domain.notice.command.application.service.NoticeContentParser.extractImageUrls;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeCommandService {

        private final NoticeRepository noticeRepository;
        private final NoticeCategoryRepository categoryRepository;
        private final NoticeImageRepository imageRepository;
        private final NoticeAttachmentRepository attachmentRepository;
        private final FileUploadService fileUploadService;

        /**
         * 본문 이미지 업로드
         */
        public String uploadImage(Long companyId, MultipartFile image) {

                StoredFile stored = fileUploadService.upload(image, FileType.IMAGE);

                NoticeImage noticeImage = NoticeImage.create(
                                companyId,
                                stored,
                                image.getOriginalFilename());

                imageRepository.save(noticeImage);
                return stored.url();
        }

        /**
         * 공지 생성
         */
        public Long create(
                        NoticeDto req,
                        List<MultipartFile> attachments) {
                NoticeCategory category = categoryRepository.findByIdAndCompanyIdAndIsDeletedNot(
                                req.getCategoryId(), req.getCompanyId(), 'Y')
                                .orElseThrow(() -> new BusinessException(NoticeErrorCode.NOT_FOUND_CATEGORY));

                Notice notice = noticeRepository.save(
                                Notice.create(
                                                req.getCompanyId(),
                                                category,
                                                req.getTitle(),
                                                req.getContent()));

                List<NoticeImage> images = extractImages(req.getContent(), req.getCompanyId());
                images.forEach(img -> img.attachToNotice(notice.getId()));

                // 첨부파일 저장
                if (attachments != null) {
                        for (MultipartFile file : attachments) {
                                StoredFile stored = fileUploadService.upload(file, FileType.ATTACHMENT);

                                attachmentRepository.save(
                                                NoticeAttachment.create(
                                                                notice.getId(),
                                                                stored,
                                                                file.getOriginalFilename()));
                        }
                }

                // 본문에 포함된 이미지 used 처리
                imageRepository.findAll().stream()
                                .filter(img -> req.getContent().contains(img.getUrl()))
                                .forEach(NoticeImage::markUsed);

                return notice.getId();
        }

        private List<NoticeImage> extractImages(String content, Long companyId) {
                return imageRepository.findAllByCompanyIdAndUsedFalse(companyId).stream()
                                .filter(img -> content.contains(img.getUrl()))
                                .toList();
        }

        public void deleteImage(Long companyId, String imageUrl) {
                NoticeImage img = imageRepository
                                .findByCompanyIdAndUrlAndUsedFalse(companyId, imageUrl)
                                .orElseThrow(() -> new BusinessException(NoticeErrorCode.NOT_FOUND_IMAGE));

                fileUploadService.delete(img.getStoredName());
                imageRepository.delete(img);
        }

        public void updateNotice(
                        Long noticeId,
                        NoticeDto req,
                        List<MultipartFile> attachments) {

                NoticeCategory category = categoryRepository.findByIdAndCompanyIdAndIsDeletedNot(
                                req.getCategoryId(), req.getCompanyId(), 'Y')
                                .orElseThrow(() -> new BusinessException(NoticeErrorCode.NOT_FOUND_CATEGORY));

                Notice notice = noticeRepository.findById(noticeId)
                                .orElseThrow(() -> new BusinessException(NoticeErrorCode.NOT_FOUND_NOTICE));

                List<NoticeImage> existingImages = imageRepository.findAllByNoticeId(noticeId);

                Set<String> usedUrls = extractImageUrls(req.getContent());

                existingImages.stream()
                                .filter(img -> !usedUrls.contains(img.getUrl()))
                                .forEach(img -> {
                                        fileUploadService.delete(img.getStoredName());
                                        imageRepository.delete(img);
                                });

                // 새로 추가된 이미지 (임시 → 연결)
                imageRepository.findAllByCompanyIdAndUsedFalse(req.getCompanyId()).stream()
                                .filter(img -> usedUrls.contains(img.getUrl()))
                                .forEach(img -> img.attachToNotice(noticeId));

                // 첨부파일 삭제 처리
                if (req.getDeletedAttachmentIds() != null && !req.getDeletedAttachmentIds().isEmpty()) {
                        List<NoticeAttachment> toDelete = attachmentRepository
                                        .findAllById(req.getDeletedAttachmentIds());
                        toDelete.forEach(att -> {
                                fileUploadService.delete(att.getStoredName());
                                attachmentRepository.delete(att);
                        });
                }

                // 새 첨부파일 저장
                if (attachments != null) {
                        for (MultipartFile file : attachments) {
                                StoredFile stored = fileUploadService.upload(file, FileType.ATTACHMENT);
                                attachmentRepository.save(
                                                NoticeAttachment.create(
                                                                noticeId,
                                                                stored,
                                                                file.getOriginalFilename()));
                        }
                }

                notice.update(
                                category,
                                req.getTitle(),
                                req.getContent());
        }

        @Transactional
        public void deleteNotice(Long noticeId, Long companyId) {

                Notice notice = noticeRepository
                                .findByIdAndCompanyId(noticeId, companyId)
                                .orElseThrow(() -> new BusinessException(NoticeErrorCode.NOT_FOUND_NOTICE));

                notice.delete();

                // 본문 이미지 삭제
                List<NoticeImage> images = imageRepository.findAllByNoticeId(noticeId);
                images.forEach(img -> {
                        fileUploadService.delete(img.getStoredName());
                        imageRepository.delete(img);
                });

                // 첨부파일 삭제
                List<NoticeAttachment> attachments = attachmentRepository.findAllByNoticeId(noticeId);

                attachments.forEach(att -> {
                        fileUploadService.delete(att.getStoredName());
                        attachmentRepository.delete(att);
                });
        }

}
