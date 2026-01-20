package org.hit.hradar.domain.salary.command.domain.repository;


import java.util.List;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Tag;
import org.hit.hradar.domain.salary.query.dto.BasicSalaryDTO;

public interface BasicSalaryRepository {

  BasicSalaryDTO findByEmpIdAndYear(Long empId, String prevYearStr);
}
