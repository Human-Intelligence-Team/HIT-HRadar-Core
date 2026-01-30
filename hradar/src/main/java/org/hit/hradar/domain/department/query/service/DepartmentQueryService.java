package org.hit.hradar.domain.department.query.service;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.department.DepartmentErrorCode;
import org.hit.hradar.domain.department.query.dto.DepartmentListResponse;
import org.hit.hradar.domain.department.query.dto.DepartmentNode;
import org.hit.hradar.domain.department.query.dto.DepartmentResponse;
import org.hit.hradar.domain.department.query.dto.OrganizationChartResponse;
import org.hit.hradar.domain.department.query.mapper.DepartmentQueryMapper;
import org.hit.hradar.domain.department.query.dto.EmployeeForOrgChartResponse;
import org.hit.hradar.domain.department.query.dto.EmployeeNode;
import org.hit.hradar.domain.employee.query.mapper.EmployeeQueryMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentQueryService {

  private final DepartmentQueryMapper departmentQueryMapper;
  private final EmployeeQueryMapper employeeQueryMapper;

  public DepartmentResponse getDepartmentById(Long deptId, Long companyId) {
    return departmentQueryMapper.findDepartmentById(deptId, companyId)
        .orElseThrow(() -> new BusinessException(DepartmentErrorCode.DEPARTMENT_NOT_FOUND));
  }

  public DepartmentListResponse getAllDepartmentsByCompany(Long companyId) {
    List<DepartmentResponse> departments =
        departmentQueryMapper.findAllDepartmentsByCompany(companyId);

    return DepartmentListResponse.of(departments);
  }

  public OrganizationChartResponse getOrganizationChart(Long companyId) {

    // 1) 부서 전체 조회
    List<DepartmentResponse> allDepartments =
        departmentQueryMapper.findAllDepartmentsByCompany(companyId);

    // 2) 조직도용 사원 조회 (부서별로 묶기)
    List<EmployeeForOrgChartResponse> allEmployees =
        employeeQueryMapper.findEmployeesForOrgChart(companyId);

    Map<Long, List<EmployeeNode>> employeesByDept =
        allEmployees.stream()
            .filter(e -> e.getDeptId() != null)
            .collect(Collectors.groupingBy(
                EmployeeForOrgChartResponse::getDeptId,
                Collectors.mapping(
                    e -> new EmployeeNode(e.getEmpId(), e.getName(), e.getPositionName()),
                    Collectors.toList()
                )
            ));

    // 3) 부서 노드 생성 + 사원 세팅
    Map<Long, DepartmentNode> departmentMap = new HashMap<>();
    for (DepartmentResponse dept : allDepartments) {
      DepartmentNode node =
          new DepartmentNode(dept.getDeptId(), dept.getDeptName(), dept.getParentDeptId());

      node.setEmployees(employeesByDept.getOrDefault(dept.getDeptId(), new ArrayList<>()));
      departmentMap.put(dept.getDeptId(), node);
    }

    // 4) 트리 구성
    List<DepartmentNode> rootNodes = new ArrayList<>();
    for (DepartmentNode node : departmentMap.values()) {
      if (node.getParentDeptId() != null) {
        DepartmentNode parent = departmentMap.get(node.getParentDeptId());
        if (parent != null) parent.getChildren().add(node);
        else rootNodes.add(node);
      } else {
        rootNodes.add(node);
      }
    }

    return OrganizationChartResponse.of(rootNodes);
  }
}

