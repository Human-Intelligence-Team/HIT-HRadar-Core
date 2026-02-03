package org.hit.hradar.domain.notice.command.domain.repository;

import org.hit.hradar.domain.notice.command.domain.aggregate.Notice;

import java.util.Optional;

public interface NoticeRepository {
    <S extends Notice> S save(S Notice);

   Optional<Notice> findByIdAndCompanyId(Long noticeId, Long companyId);

    Optional<Notice> findById(Long noticeId);
}
