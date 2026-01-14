package org.hit.hradar.domain.competencyReport.command.domain.repository;

import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Content;

public interface ContentRepository {

  Content save(Content content);

}
