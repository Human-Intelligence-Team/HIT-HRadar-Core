package org.hit.hradar.domain.notice.command.domain.repository;

import org.hit.hradar.domain.notice.command.domain.aggregate.NoticeCategory;

import java.util.Optional;

public interface NoticeCategoryRepository {
    boolean existsByCompanyIdAndNameAndIsDeletedNot(Long companyId, String name, Character isDeleted);

    <S extends NoticeCategory> S save(S NoticeCategory);

    Optional<NoticeCategory> findByIdAndCompanyId(Long categoryId, Long companyId);
}
