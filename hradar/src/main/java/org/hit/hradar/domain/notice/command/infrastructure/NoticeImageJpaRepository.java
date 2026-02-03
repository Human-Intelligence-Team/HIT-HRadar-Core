package org.hit.hradar.domain.notice.command.infrastructure;

import org.hit.hradar.domain.notice.command.domain.aggregate.NoticeImage;
import org.hit.hradar.domain.notice.command.domain.repository.NoticeImageRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeImageJpaRepository extends NoticeImageRepository, JpaRepository<NoticeImage, Integer> {
}
