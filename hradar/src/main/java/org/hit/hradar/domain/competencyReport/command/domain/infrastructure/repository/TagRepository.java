package org.hit.hradar.domain.competencyReport.command.domain.infrastructure.repository;


import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Tag;

public interface TagRepository{

  Boolean existsByTagName(String tagName);
  Tag save(Tag tag);
}
