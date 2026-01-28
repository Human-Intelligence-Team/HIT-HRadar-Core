package org.hit.hradar.domain.employee.command.application.service;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.EmployeeErrorCode;
import org.hit.hradar.domain.employee.command.application.dto.request.UpdateEmployeeAssignmentRequest;
import org.hit.hradar.domain.employee.command.application.dto.reponse.UpdateEmployeeAssignmentResponse;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.aggregate.EmployeeMovementHistory;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.domain.employee.command.infrastructure.EmployeeMovementHistoryJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeUpdateApplicationService {

  private final EmployeeRepository employeeRepository;
  private final EmployeeMovementHistoryJpaRepository movementHistoryRepo;


  // 사원 인사기록
  @Transactional
  public UpdateEmployeeAssignmentResponse updateAssignment(Long comId, Long changerAccId, Long empId,
      UpdateEmployeeAssignmentRequest req) {

    Employee emp = employeeRepository.findByEmpIdAndComIdAndIsDeleted(empId, comId, 'N')
        .orElseThrow(() -> new BusinessException(EmployeeErrorCode.EMPLOYEE_ERROR_CODE));

    Long beforeDept = emp.getDeptId();
    Long beforePos = emp.getPositionId();
    String beforeNo = emp.getEmployeeNo();

    // 선택지 B: null/blank -> null (사번 삭제 처리)
    String employeeNo = (req.getEmployeeNo() == null || req.getEmployeeNo().trim().isEmpty())
        ? null
        : req.getEmployeeNo().trim();

    // 사번 유니크 체크(새 값 설정하는 경우에만)
    if (employeeNo != null && (beforeNo == null || !beforeNo.equals(employeeNo))) {
      if (employeeRepository.existsByEmployeeNoAndComIdAndIsDeleted( employeeNo, comId,'N')) {
        throw new BusinessException(EmployeeErrorCode.DUPLICATE_EMPLOYEE_NO_OR_EMAIL);
      }
    }

    // 반영
    emp.changeDepartment(req.getDeptId());
    emp.changePosition(req.getPositionId());
    emp.changeEmployeeNo(employeeNo);

    boolean deptChanged = !eq(beforeDept, emp.getDeptId());
    boolean posChanged = !eq(beforePos, emp.getPositionId());

    // 사번은 이력에 남기지 않음: 부서/직위 변화가 없으면 history 저장하지 않음
    Long movementId = null;
    if (deptChanged || posChanged) {
      LocalDate effective = (req.getEffectiveDate() != null) ? req.getEffectiveDate() : LocalDate.now();

      EmployeeMovementHistory saved = movementHistoryRepo.save(
          EmployeeMovementHistory.builder()
              .empId(empId)
              .fromDeptId(beforeDept)
              .toDeptId(emp.getDeptId())
              .fromPositionId(beforePos)
              .toPositionId(emp.getPositionId())
              // 아래 필드들은 DB에서 NULL 허용이어야 함
              .fromEmpAccRoleId(null)
              .toEmpAccRoleId(null)
              .eventReason(req.getEventReason())
              .effectiveDate(effective)
              .build()
      );
      movementId = saved.getMovementId(); // PK 반환
    }

    return UpdateEmployeeAssignmentResponse.builder()
        .empId(emp.getEmpId())
        .deptId(emp.getDeptId())
        .positionId(emp.getPositionId())
        .employeeNo(emp.getEmployeeNo())
        .movementId(movementId) // 이력 없으면 null
        .build();
  }

  private boolean eq(Object a, Object b) {
    return (a == null) ? b == null : a.equals(b);
  }
}
