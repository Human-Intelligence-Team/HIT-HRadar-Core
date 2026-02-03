package org.hit.hradar.domain.competencyReport.command.domain.repository;


import java.util.List;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Tag;

public interface TagRepository{

  Boolean existsByTagName(String tagName);
  Tag save(Tag tag);

  void deleteByTagIdIn(List<Long> tagIds);
}
