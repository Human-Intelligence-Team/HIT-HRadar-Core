package org.hit.hradar.domain.employee.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.EmployeeErrorCode;
import org.hit.hradar.domain.employee.query.dto.EmployeeResponse;
import org.hit.hradar.domain.employee.query.mapper.EmployeeQueryMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeQueryService {

    private final EmployeeQueryMapper employeeQueryMapper;

    public EmployeeResponse getEmployeeById(Long empId, Long comId) {
        return employeeQueryMapper.findEmployeeById(empId, comId)
                .orElseThrow(() -> new BusinessException(EmployeeErrorCode.EMPLOYEE_NOT_FOUND));
    }

    public List<EmployeeResponse> getAllEmployeesByCompany(Long comId) {
        return employeeQueryMapper.findAllEmployeesByCompany(comId);
    }


  }