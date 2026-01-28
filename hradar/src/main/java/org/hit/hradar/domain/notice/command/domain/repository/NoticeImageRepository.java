package org.hit.hradar.domain.notice.command.domain.repository;

import org.hit.hradar.domain.notice.command.domain.aggregate.NoticeImage;

import java.util.List;
import java.util.Optional;


public interface NoticeImageRepository {
    <S extends NoticeImage> S save(S NoticeImage);
    List<NoticeImage> findAll();

    List<NoticeImage> findAllByCompanyIdAndUsedFalse(Long companyId);

    Optional<NoticeImage> findByCompanyIdAndUrlAndUsedFalse(Long companyId, String imageUrl);

    void delete(NoticeImage img);

    List<NoticeImage> findAllByNoticeId(Long noticeId);
}
