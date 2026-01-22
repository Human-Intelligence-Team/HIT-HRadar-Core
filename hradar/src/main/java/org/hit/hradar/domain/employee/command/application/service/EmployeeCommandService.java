package org.hit.hradar.domain.employee.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.department.command.domain.repository.DepartmentRepository;
import org.hit.hradar.domain.employee.EmployeeErrorCode;
import org.hit.hradar.domain.employee.command.application.dto.UpdateEmployeeRequest;
import org.hit.hradar.domain.employee.command.application.dto.CreateFirstEmpRequest; // Import CreateFirstEmpRequest
import org.hit.hradar.domain.employee.command.application.dto.CreateFirstEmpResponse; // Import CreateFirstEmpResponse
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.domain.positions.command.domain.repository.PositionRepository;
import org.hit.hradar.domain.rolePermission.command.infrastructure.RoleEmpJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeCommandService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final RoleEmpJpaRepository roleEmpJpaRepository; // Add RoleEmpRepository

    /**
     * 회사 생성자(첫 사원)
     * - defaultRoleId는 상위 서비스에서 결정해서 전달
     * - additionalRoleId(OWNER)도 상위 서비스에서 결정해서 전달
     */
    @Transactional
    public CreateFirstEmpResponse  createFirstEmployee(CreateFirstEmpRequest req) {

        Employee emp = Employee.builder()
                .comId(req.getComId())
                .deptId(null)
                .positionId(null)
                .name(req.getName())
                .email(req.getEmail())
                .employeeNo(null)
                .build();

        Employee saved = employeeRepository.save(emp);



        return CreateFirstEmpResponse.builder()
                .empId(saved.getEmpId())
                .comId(saved.getComId())
                .build();
    }

//    /**
//     * 일반 사원 생성
//     */
//    @Transactional
//    public Long createEmployee(CreateEmployeeRequest request, Long comId) {
//        // Validate department existence
//        if (request.getDeptId() != null) {
//            departmentRepository.findByDeptIdAndComIdAndIsDeleted(request.getDeptId(), comId, 'N')
//                    .orElseThrow(() -> new BusinessException(EmployeeErrorCode.INVALID_DEPARTMENT));
//        }
//
//        // Validate position existence
//        if (request.getPositionId() != null) {
//            positionRepository.findByPositionIdAndComIdAndIsDeleted(request.getPositionId(), comId, 'N')
//                    .orElseThrow(() -> new BusinessException(EmployeeErrorCode.INVALID_POSITION));
//        }
//
//        // Validate employee number and email uniqueness
//        if (employeeRepository.existsByEmployeeNoAndComIdAndIsDeleted(request.getEmployeeNo(), comId, 'N') ||
//            employeeRepository.existsByEmailAndComIdAndIsDeleted(request.getEmail(), comId, 'N')) {
//            throw new BusinessException(EmployeeErrorCode.DUPLICATE_EMPLOYEE_NO_OR_EMAIL);
//        }
//
//        Employee employee = Employee.builder()
//                .comId(comId)
//                .deptId(request.getDeptId())
//                .positionId(request.getPositionId())
//                .name(request.getName())
//                .employeeNo(request.getEmployeeNo())
//                .email(request.getEmail())
//                .gender(request.getGender())
//                .birth(request.getBirth())
//                .hireDate(request.getHireDate())
//                .exitDate(request.getExitDate())
//                .image(request.getImage())
//                .extNo(request.getExtNo())
//                .phoneNo(request.getPhoneNo())
//                .note(request.getNote())
//                .employmentType(request.getEmploymentType())
//                .build();
//        employeeRepository.save(employee);
//        return employee.getEmpId();
//    }

    @Transactional
    public void updateEmployee(Long empId, Long comId, UpdateEmployeeRequest request) {
        Employee employee = employeeRepository.findByEmpIdAndComIdAndIsDeleted(empId, comId, 'N')
                .orElseThrow(() -> new BusinessException(EmployeeErrorCode.EMPLOYEE_NOT_FOUND));

        if (request.getDeptId() != null) {
            departmentRepository.findByDeptIdAndCompanyIdAndIsDeleted(request.getDeptId(), comId, 'N')
                    .orElseThrow(() -> new BusinessException(EmployeeErrorCode.INVALID_DEPARTMENT));
        }

        if (request.getPositionId() != null) {
            positionRepository.findByPositionIdAndComIdAndIsDeleted(request.getPositionId(), comId, 'N')
                    .orElseThrow(() -> new BusinessException(EmployeeErrorCode.INVALID_POSITION));
        }

      if (request.getEmployeeNo() != null && !request.getEmployeeNo().equals(employee.getEmployeeNo()) &&
            employeeRepository.existsByEmployeeNoAndComIdAndIsDeleted(request.getEmployeeNo(), comId, 'N')) {
            throw new BusinessException(EmployeeErrorCode.DUPLICATE_EMPLOYEE_NO_OR_EMAIL);
        }
        if (!employee.getEmail().equals(request.getEmail()) &&
            employeeRepository.existsByEmailAndComIdAndIsDeleted(request.getEmail(), comId, 'N')) {
            throw new BusinessException(EmployeeErrorCode.DUPLICATE_EMPLOYEE_NO_OR_EMAIL);
        }

        employee.updateEmployee(
                request.getDeptId(), request.getPositionId(), request.getName(),
                request.getEmployeeNo(), request.getEmail(), request.getGender(),
                request.getBirth(), request.getHireDate(), request.getExitDate(),
                request.getImage(), request.getExtNo(), request.getPhoneNo(),
                request.getNote(), request.getEmploymentType()
        );
    }

    @Transactional
    public void deleteEmployee(Long empId, Long comId) {
        Employee employee = employeeRepository.findByEmpIdAndComIdAndIsDeleted(empId, comId, 'N')
                .orElseThrow(() -> new BusinessException(EmployeeErrorCode.EMPLOYEE_NOT_FOUND));
        employee.markAsDeleted();
    }
}