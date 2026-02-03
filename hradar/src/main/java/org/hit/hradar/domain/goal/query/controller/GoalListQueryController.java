package org.hit.hradar.domain.goal.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.domain.goal.query.dto.response.GoalNodeResponseDto;
import org.hit.hradar.domain.goal.query.service.GoalListQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/*
*  - 팀(department)에 속한 모든 Goal 트리를 조회
* - 각 Goal의 "현재 진행률"만 포함
* */
@Tag(name = "Goal Tree Query", description = "목표 트리 및 목록 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/goals")
public class GoalListQueryController {

        private final GoalListQueryService goalQueryService;
        private final EmployeeRepository employeeRepository;

        // 팀 Goal 트리 조회
        @GetMapping()
        public ResponseEntity<ApiResponse<List<GoalNodeResponseDto>>> getGoalTree(
                        @CurrentUser AuthUser employee) {
                Long empId = employee.employeeId();
                Employee emp = employeeRepository.findById(empId).orElse(null);

                Long deptId = Objects.requireNonNull(emp).getDeptId();

                // Query Service 호출 (모든 계산은 Service에서 끝남)
                List<GoalNodeResponseDto> result = goalQueryService.getGoalTree(deptId);

                // 공통 ApiResponse 래핑
                return ResponseEntity.ok(ApiResponse.success(result));
        }

        // 내 목표 조회
        @GetMapping("/me")
        public ResponseEntity<ApiResponse<List<GoalNodeResponseDto>>> getMyGoal(
                        @CurrentUser AuthUser employee) {
                Long empId = employee.employeeId();

                List<GoalNodeResponseDto> result = goalQueryService.getMyGoal(empId);

                return ResponseEntity.ok(ApiResponse.success(result));
        }

        // 조직 전체 목표 조회
        @GetMapping("/all")
        public ResponseEntity<ApiResponse<List<GoalNodeResponseDto>>> getDepartmentGoals(
                        @RequestParam Long departmentId) {
                List<GoalNodeResponseDto> result = goalQueryService.getGoalTree(departmentId);

                return ResponseEntity.ok(ApiResponse.success(result));
        }
}
