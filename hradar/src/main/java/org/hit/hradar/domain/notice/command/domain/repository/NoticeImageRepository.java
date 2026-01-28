package org.hit.hradar.domain.notice.command.domain.repository;

import org.hit.hradar.domain.notice.command.domain.aggregate.NoticeAttachment;
import org.hit.hradar.domain.notice.command.domain.aggregate.NoticeImage;

import java.util.List;


public interface NoticeImageRepository {
    <S extends NoticeImage> S save(S NoticeImage);
    List<NoticeImage> findAll();
}
