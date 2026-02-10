package org.hit.hradar.domain.department.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.department.query.dto.DepartmentResponse;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DepartmentQueryMapper {

    Optional<DepartmentResponse> findDepartmentById(Long deptId, Long comId);

    List<DepartmentResponse> findAllDepartmentsByCompany(Long comId);

}
