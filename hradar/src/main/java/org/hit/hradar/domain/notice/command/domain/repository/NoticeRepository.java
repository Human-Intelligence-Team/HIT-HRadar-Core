package org.hit.hradar.domain.notice.command.domain.repository;

import org.hit.hradar.domain.notice.command.domain.aggregate.Notice;

public interface NoticeRepository {
    <S extends Notice> S save(S Notice);
}
