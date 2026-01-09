package org.hit.hradar.domain.employee.command.domain.repository;

import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  // 회사 내 사번 중복 방지/조회용
  Optional<Employee> findByComIdAndEmployeeNo(Long comId, String employeeNo);

  boolean existsByComIdAndEmployeeNo(Long comId, String employeeNo);

  // 회사 내 이메일 중복/조회용 (email이 null 허용이면 Optional로)
  Optional<Employee> findByComIdAndEmail(Long comId, String email);

  boolean existsByComIdAndEmail(Long comId, String email);

  // 회사별 전체 조회 (삭제 제외)
  List<Employee> findAllByComIdAndIsDeletedFalse(Long comId);

  // 부서별 조회 (삭제 제외)
  List<Employee> findAllByComIdAndDeptIdAndIsDeletedFalse(Long comId, Long deptId);

  // 단건 조회 (삭제 제외)
  Optional<Employee> findByEmpIdAndIsDeletedFalse(Long empId);
}