package org.hit.hradar.domain.notice.command.infrastructure;

import org.hit.hradar.domain.notice.command.domain.aggregate.NoticeCategory;
import org.hit.hradar.domain.notice.command.domain.repository.NoticeCategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeCategoryJpaRepository extends NoticeCategoryRepository, JpaRepository<NoticeCategory, Long> {
}
