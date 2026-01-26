package org.hit.hradar.domain.department.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.department.DepartmentErrorCode;
import org.hit.hradar.domain.department.query.dto.DepartmentNode;
import org.hit.hradar.domain.department.query.dto.DepartmentResponse;
import org.hit.hradar.domain.department.query.mapper.DepartmentQueryMapper;
//import org.hit.hradar.domain.employee.query.dto.EmployeeForOrgChartResponse;
//import org.hit.hradar.domain.employee.query.dto.EmployeeNode;
import org.hit.hradar.domain.employee.query.mapper.EmployeeQueryMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<DepartmentResponse> getAllDepartmentsByCompany(Long companyId) {
        return departmentQueryMapper.findAllDepartmentsByCompany(companyId);
    }

    public List<DepartmentNode> getOrganizationChart(Long companyId) {

        List<DepartmentResponse> allDepartments = departmentQueryMapper.findAllDepartmentsByCompany(companyId);
        Map<Long, DepartmentNode> departmentMap = new HashMap<>();
        List<DepartmentNode> rootNodes = new ArrayList<>();

//
//        List<EmployeeForOrgChartResponse> allEmployees = employeeQueryMapper.findEmployeesForOrgChart(companyId);
//        Map<Long, List<EmployeeNode>> employeesByDept = allEmployees.stream()
//                .filter(e -> e.getDeptId() != null)
//                .collect(Collectors.groupingBy(
//                        EmployeeForOrgChartResponse::getDeptId,
//                        Collectors.mapping(e -> new EmployeeNode(e.getEmpId(), e.getName(), e.getPositionName()), Collectors.toList())
//                ));
//
//
//        for (DepartmentResponse dept : allDepartments) {
//            DepartmentNode node = new DepartmentNode(dept.getDeptId(), dept.getDeptName(), dept.getParentDeptId());
//            node.setEmployees(employeesByDept.getOrDefault(dept.getDeptId(), new ArrayList<>()));
//            departmentMap.put(dept.getDeptId(), node);
//        }


        for (DepartmentNode node : departmentMap.values()) {
            if (node.getParentDeptId() != null) {
                DepartmentNode parent = departmentMap.get(node.getParentDeptId());
                if (parent != null) {
                    parent.getChildren().add(node);
                } else {
                    rootNodes.add(node);
                }
            } else {
                rootNodes.add(node);
            }
        }
        return rootNodes;
    }
}
