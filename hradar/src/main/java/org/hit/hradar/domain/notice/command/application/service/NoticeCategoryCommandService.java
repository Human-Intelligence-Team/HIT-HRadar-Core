package org.hit.hradar.domain.notice.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.notice.NoticeErrorCode;
import org.hit.hradar.domain.notice.command.application.dto.NoticeCategoryDto;
import org.hit.hradar.domain.notice.command.application.dto.NoticeCategoryRequest;
import org.hit.hradar.domain.notice.command.domain.aggregate.NoticeCategory;
import org.hit.hradar.domain.notice.command.domain.repository.NoticeCategoryRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeCategoryCommandService {

    private final NoticeCategoryRepository noticeCategoryRepository;

    public Long create(NoticeCategoryDto req) {

        if (noticeCategoryRepository.existsByCompanyIdAndNameAndIsDeletedNot(
                req.getCompanyId(), req.getName(), 'Y'
        )) {
            throw new BusinessException(NoticeErrorCode.DUPLICATED_CATEGORY_NAME);
        }

        NoticeCategory category = NoticeCategory.create(req.getCompanyId(), req.getName());

        return noticeCategoryRepository.save(category).getId();
    }

    public void update(
            Long categoryId,
            NoticeCategoryDto req
    ) {
        NoticeCategory category =
                noticeCategoryRepository.findByIdAndCompanyId(
                        categoryId, req.getCompanyId()
                ).orElseThrow(() ->
                        new BusinessException(NoticeErrorCode.NOT_FOUND_CATEGORY)
                );

        category.update(req.getName());
    }

    public void delete(Long categoryId, Long companyId) {
        NoticeCategory category =
                noticeCategoryRepository.findByIdAndCompanyId(
                        categoryId, companyId
                ).orElseThrow(() ->
                        new BusinessException(NoticeErrorCode.NOT_FOUND_CATEGORY)
                );

        category.delete();
    }
}
