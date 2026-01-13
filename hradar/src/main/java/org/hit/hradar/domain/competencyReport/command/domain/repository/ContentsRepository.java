package org.hit.hradar.domain.competencyReport.command.domain.repository;

import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Content;

public interface ContentsRepository {

  Content save(Content content);
}
