package org.hit.hradar.domain.competencyReport.command.domain.repository;

import java.util.Optional;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Content;

public interface ContentRepository {

  Content save(Content content);
  Optional<Content> findById(Long contentId);

}
