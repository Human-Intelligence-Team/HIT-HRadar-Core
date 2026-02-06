-- 최종 통합 테스트 데이터 (INSERT ONLY - Admin 제외)
-- Admin(ID 1)은 시스템 초기화 시 생성되므로 2번부터 추가
-- 실행 방법: mysql -u root -p hradar < src/main/resources/sql/final_test_data_insert_only.sql

-- 1. 회사 신청 (Audit 없음)
INSERT IGNORE INTO company_application (application_id, address, biz_no, company_telephone, company_name, creater_email, creater_login_id, creater_name, status) 
VALUES (1, '서울시 강남구 테헤란로', '123-45-67890', '02-1234-5678', '테스트컴퍼니', 'admin@hradar.com', 'admin', '관리자', 'APPROVED');

-- 2. 회사
INSERT IGNORE INTO company (com_id, application_id, company_code, name, biz_no, address, status, is_deleted, created_at, created_by) 
VALUES (1, 1, 'TEST001', '테스트컴퍼니', '123-45-67890', '서울시 강남구 테헤란로', 'APPROVED', 'N', NOW(), 1);

-- 3. 직위
INSERT IGNORE INTO company_position (position_id, com_id, `rank`, name, is_deleted, created_at, created_by) VALUES 
(1, 1, 1, '사원', 'N', NOW(), 1),
(2, 1, 2, '대리', 'N', NOW(), 1),
(3, 1, 3, '과장', 'N', NOW(), 1),
(4, 1, 4, '팀장', 'N', NOW(), 1),
(5, 1, 5, '임원', 'N', NOW(), 1),
(6, 1, 6, '대표이사', 'N', NOW(), 1);

-- 4. 부서
INSERT IGNORE INTO department (dept_id, com_id, dept_name, is_deleted, created_at, created_by) VALUES 
(1, 1, '경영지원본부', 'N', NOW(), 1),
(2, 1, '인사팀', 'N', NOW(), 1),
(3, 1, '개발본부', 'N', NOW(), 1),
(4, 1, '플랫폼개발팀', 'N', NOW(), 1);

-- 5. 사원 (1번 김대표/Admin 제외, 2번부터 시작)
INSERT IGNORE INTO employee (emp_id, com_id, dept_id, position_id, name, employee_no, email, type, is_deleted, created_at, created_by) VALUES 
(2, 1, 2, 4, '이인사', 'E002', 'hr_mgr@test.com', 'WORKING', 'N', NOW(), 1),
(3, 1, 4, 4, '박개발', 'E003', 'dev_mgr@test.com', 'WORKING', 'N', NOW(), 1),
(4, 1, 4, 1, '최사원', 'E004', 'dev_staff@test.com', 'WORKING', 'N', NOW(), 1),
(5, 1, 2, 2, '정HR', 'E005', 'hr_staff@test.com', 'WORKING', 'N', NOW(), 1);

-- 6. 사용자 계정 (1번 admin 제외, 2번부터 시작)
-- 비밀번호는 모두 '1234'
INSERT IGNORE INTO user_account (account_id, company_code, com_id, employee_id, login_id, email, name, password, role, status, is_deleted) VALUES 
(2, 'TEST001', 1, 2, 'hr_manager', 'hr_mgr@test.com', '이인사', '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'ADMIN', 'ACTIVE', 'N'),
(3, 'TEST001', 1, 3, 'dev_manager', 'dev_mgr@test.com', '박개발', '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'USER', 'ACTIVE', 'N'),
(4, 'TEST001', 1, 4, 'dev_staff', 'dev_staff@test.com', '최사원', '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'USER', 'ACTIVE', 'N'),
(5, 'TEST001', 1, 5, 'hr_staff', 'hr_staff@test.com', '정HR', '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'USER', 'ACTIVE', 'N');

-- 7. 권한 - 라우터 경로 100% 매핑
INSERT IGNORE INTO permission (perm_key, name, route_path, is_deleted, created_at, created_by) VALUES
-- 공통/내 정보
('POLICY_READ', '정책 조회', '/policy', 'N', NOW(), 1),
('NOTICE_READ', '공지 조회', '/notice', 'N', NOW(), 1),
('NOTICE_MANAGE', '공지 관리', '/notice/create', 'N', NOW(), 1),
('MY_PROFILE', '내 정보', '/my-profile', 'N', NOW(), 1),
('MY_DEPT', '내 부서', '/my-department', 'N', NOW(), 1),
-- 조직/인사
('DEPT_LIST', '부서 목록', '/organization', 'N', NOW(), 1),
('ORG_CHART', '조직도', '/department/org-chart', 'N', NOW(), 1),
('DEPT_MANAGE', '부서 관리', '/department/manage', 'N', NOW(), 1),
('EMP_MANAGE', '사원 관리', '/employee', 'N', NOW(), 1),
('EMP_LIST_READ', '사원 목록 조회', '/personnel/employees/list', 'N', NOW(), 1),
('POS_MANAGE', '직위 관리', '/personnel/positions', 'N', NOW(), 1),
('POS_read', '직위 목록', '/personnel/positions/list', 'N', NOW(), 1),
('EMP_APPOINT', '인사 발령', '/personnel/appointment', 'N', NOW(), 1),
('EMP_HISTORY', '발령 이력', '/personnel/history', 'N', NOW(), 1),
-- 회사
('COM_MY', '내 회사', '/company/my', 'N', NOW(), 1),
('COM_MANAGE_MY', '회사 관리', '/company/my-manage', 'N', NOW(), 1),
('ROLE_MANAGE', '역할 관리', '/company/roles', 'N', NOW(), 1),
('COM_MANAGE_ALL', '회사 전체 관리', '/company/manage', 'N', NOW(), 1),
-- 성과/목표
('GOAL_LIST', '목표 목록', '/goal', 'N', NOW(), 1),
('GOAL_DASH_HR', 'HR 목표 대시보드', '/hr/goals', 'N', NOW(), 1),
('GOAL_TEAM_LIST', '팀 목표 목록', '/to/goals', 'N', NOW(), 1),
('GOAL_CREATE', '목표 생성', '/goal/create', 'N', NOW(), 1),
('REPORT_ALL', '리포트 전체', '/all/competency/report', 'N', NOW(), 1),
('REPORT_DEPT', '리포트 부서', '/dept/competency/report', 'N', NOW(), 1),
('REPORT_ME', '리포트 개인', '/me/competency/report', 'N', NOW(), 1),
('SALARY_DASH', '급여 대시보드', '/salary/dashboard', 'N', NOW(), 1),
-- 급여
('SALARY_BASIC_ALL', '기본급 전체', '/all/salary/basic', 'N', NOW(), 1),
('SALARY_BASIC_ME', '내 기본급', '/me/salary/basic', 'N', NOW(), 1),
('SALARY_COMP_ALL', '변동급 전체', '/all/salary/compensation', 'N', NOW(), 1),
-- 콘텐츠
('CONTENT_ALL', '콘텐츠 전체', '/all/contents', 'N', NOW(), 1),
('TAG_MANAGE', '태그 관리', '/contents/tag', 'N', NOW(), 1),
-- 회차/평가
('CYCLE_MANAGE', '회차 관리', '/cycles', 'N', NOW(), 1),
('CYCLE_MANAGE_HR', 'HR 회차 관리', '/hr/cycles', 'N', NOW(), 1),
('GRADE_SETTING', '등급 설정', '/grade/setting', 'N', NOW(), 1),
('GRADE_LIST_DEPT', '부서 등급', '/grading/list', 'N', NOW(), 1),
('GRADE_LIST_HR', 'HR 등급', '/hr/grading/list', 'N', NOW(), 1),
('GRADE_ASSIGN', '등급 부여', '/to/grading/list', 'N', NOW(), 1),
('GRADE_APPROVE', '등급 승인', '/hr/grading/list/approve', 'N', NOW(), 1),
('GRADE_MY', '내 등급', '/my/grading', 'N', NOW(), 1),
('GRADE_OBJECTION', '이의제기', '/to/grading/objection', 'N', NOW(), 1),
-- 평가 설정
('EVAL_TYPE', '평가 유형', '/hr/evaluation/type/setting', 'N', NOW(), 1),
('EVAL_FORM', '평가 문항', '/hr/evaluation/question/form/setting', 'N', NOW(), 1),
('EVAL_ASSIGN', '평가 배정', '/hr/evaluation/assignment', 'N', NOW(), 1),
('EVAL_STATUS', '배정 현황', '/hr/evaluation/assignment/status', 'N', NOW(), 1),
('EVAL_EXEC', '평가 수행', '/evaluation/assignment/response', 'N', NOW(), 1),
('EVAL_RESULT_HR', '평가 결과(HR)', '/hr/evaluation/response/result', 'N', NOW(), 1),
('EVAL_RESULT_MY', '평가 결과(나)', '/evaluation/response/my/result', 'N', NOW(), 1),
-- 대시보드
('DASH_MY', '내 대시보드', '/my/dashboard', 'N', NOW(), 1),
('DASH_HR', 'HR 대시보드', '/hr/dashboard', 'N', NOW(), 1),
-- 결재
('APPR_TYPE', '결재 양식', '/approval/approval-document-types', 'N', NOW(), 1),
('APPR_CREATE', '기안 작성', '/approval/create', 'N', NOW(), 1),
('APPR_MY', '내 문서함', '/approval/my-documents', 'N', NOW(), 1),
('APPR_ALL', '전체 문서함', '/approval/all-documents', 'N', NOW(), 1),
('APPR_ADMIN', '결재 관리', '/approval/admin', 'N', NOW(), 1),
-- 근태
('ATT_COMMUTE', '내 출퇴근', '/attendance/commute', 'N', NOW(), 1),
('ATT_IP', 'IP 정책', '/attendance/ip-policy', 'N', NOW(), 1),
('ATT_DEPT', '부서 근태', '/attendance/department', 'N', NOW(), 1),
('ATT_CAL', '근태 캘린더', '/attendance/department-calendar', 'N', NOW(), 1),
-- 휴가
('LEAVE_MY', '내 휴가', '/leave/my-history', 'N', NOW(), 1),
('LEAVE_POLICY', '휴가 정책', '/leave/policy', 'N', NOW(), 1),
('LEAVE_DEPT', '부서 휴가', '/leave/admin/department-history', 'N', NOW(), 1);

-- 8. 역할
INSERT IGNORE INTO role (role_id, com_id, is_system, role_key, name, is_deleted, created_at, created_by) VALUES
(1, 1, 'Y', 'ADMIN', '최고 관리자', 'N', NOW(), 1),
(2, 1, 'Y', 'EMPLOYEE', '일반 사원', 'N', NOW(), 1),
(3, 1, 'N', 'HR_ADMIN', '인사 관리자', 'N', NOW(), 1),
(4, 1, 'N', 'TEAM_LEADER', '부서장', 'N', NOW(), 1);

-- 9. 역할-권한 매핑
-- ADMIN: 모든 권한
INSERT IGNORE INTO role_permission (role_id, perm_id, created_at, created_by) 
SELECT 1, perm_id, NOW(), 1 FROM permission;

-- EMPLOYEE: 기본 권한 (내정보, 공지, 결재, 휴가, 근태, 평가, 리포트)
INSERT IGNORE INTO role_permission (role_id, perm_id, created_at, created_by) 
SELECT 2, perm_id, NOW(), 1 FROM permission WHERE perm_key IN (
  'MY_PROFILE', 'MY_DEPT', 'POLICY_READ', 'NOTICE_READ',
  'ATT_COMMUTE', 'LEAVE_MY', 'APPR_MY', 'APPR_CREATE',
  'DASH_MY', 'SALARY_BASIC_ME', 'EVAL_EXEC', 'EVAL_RESULT_MY', 
  'REPORT_ME', 'CONTENT_ALL', 'GRADE_MY', 'GOAL_LIST'
);

-- HR_ADMIN
INSERT IGNORE INTO role_permission (role_id, perm_id, created_at, created_by)
SELECT 3, perm_id, NOW(), 1 FROM permission WHERE perm_key IN (
  'MY_PROFILE', 'MY_DEPT', 'POLICY_READ', 'NOTICE_READ', 'NOTICE_MANAGE',
  'DEPT_LIST', 'ORG_CHART', 'DEPT_MANAGE', 
  'EMP_MANAGE', 'EMP_LIST_READ', 'POS_MANAGE', 'POS_read', 'EMP_APPOINT', 'EMP_HISTORY',
  'ATT_COMMUTE', 'ATT_IP', 'ATT_DEPT', 'ATT_CAL',
  'LEAVE_MY', 'LEAVE_POLICY', 'LEAVE_DEPT',
  'GOAL_DASH_HR', 'SALARY_BASIC_ALL', 'SALARY_DASH', 'SALARY_COMP_ALL',
  'EVAL_TYPE', 'EVAL_FORM', 'EVAL_ASSIGN', 'EVAL_STATUS', 'EVAL_RESULT_HR',
  'GRADE_SETTING', 'GRADE_LIST_HR', 'GRADE_APPROVE',
  'DASH_HR'
);

-- TEAM_LEADER
INSERT IGNORE INTO role_permission (role_id, perm_id, created_at, created_by)
SELECT 4, perm_id, NOW(), 1 FROM permission WHERE perm_key IN (
  'MY_PROFILE', 'MY_DEPT', 'POLICY_READ', 'NOTICE_READ',
  'ATT_COMMUTE', 'LEAVE_MY', 'APPR_MY', 'APPR_CREATE',
  'GOAL_TEAM_LIST', 'GRADE_LIST_DEPT', 'GRADE_ASSIGN', 'GRADE_OBJECTION',
  'REPORT_DEPT'
);

-- 10. 사원-역할 매핑
-- admin(1번) 제외
-- hr_manager
INSERT IGNORE INTO role_employee (role_id, emp_id, created_at, created_by) VALUES (1, 2, NOW(), 1);
-- dev_manager
INSERT IGNORE INTO role_employee (role_id, emp_id, created_at, created_by) VALUES (4, 3, NOW(), 1);
-- dev_staff
INSERT IGNORE INTO role_employee (role_id, emp_id, created_at, created_by) VALUES (2, 4, NOW(), 1);
-- hr_staff
INSERT IGNORE INTO role_employee (role_id, emp_id, created_at, created_by) VALUES (3, 5, NOW(), 1);

COMMIT;
