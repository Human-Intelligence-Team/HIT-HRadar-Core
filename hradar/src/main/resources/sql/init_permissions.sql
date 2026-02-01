/*
 * Role & Permission Initialization SQL
 * 
 * Frontend Router(router/index.js) 구조를 기반으로 모든 모듈에 대한 더미 권한을 생성합니다.
 * Company ID = 1 (Dummy Company) 기준입니다.
 */

-- 1. 권한 (Permission)
-- INSERT IGNORE로 중복 방지
INSERT IGNORE INTO permission (perm_key, name, route_path, description, is_deleted, created_at, created_by) VALUES
-- [기본/관리]
('USER_MANAGE',       '사용자 관리',        '/employee',            '사원 목록, 입사/퇴사 처리 및 계정 관리', 'N', NOW(), 1),
('DEPT_MANAGE',       '부서/조직 관리',     '/department/manage',   '부서 생성/수정/이동 및 조직도 관리', 'N', NOW(), 1),
('COMPANY_MANAGE',    '회사 정보 관리',     '/company/manage',      '회사 정보 수정 및 사업장 관리', 'N', NOW(), 1),
('ROLE_MANAGE',       '역할/권한 관리',     '/company/roles',       '시스템 권한 및 커스텀 역할 관리', 'N', NOW(), 1),
('NOTICE_MANAGE',     '공지사항 관리',      '/notice',              '공지사항 작성/수정/삭제', 'N', NOW(), 1),

-- [공통/조회]
('MY_PROFILE_READ',   '내 정보 조회',       '/my-profile',          '나의 인사 정보 및 프로필 조회', 'N', NOW(), 1),
('MY_PROFILE_UPDATE', '내 정보 수정',       '/my-profile',          '나의 정보 수정 (비밀번호 등)', 'N', NOW(), 1),
('NOTICE_READ',       '공지사항 조회',      '/notice',              '전사 공지사항 열람', 'N', NOW(), 1),
('POLICY_READ',       '규정/정책 조회',     '/policy',              '사내 규정 및 정책 열람', 'N', NOW(), 1),
('DEPT_READ',         '조직도 조회',        '/organization',        '전사 조직도 및 부서원 연락처 조회', 'N', NOW(), 1),

-- [목표/성과]
('GOAL_MANAGE',       '성과/목표 관리(HR)', '/hr/goals',            '전사 목표 설정 및 성과 관리 (관리자용)', 'N', NOW(), 1),
('GOAL_READ',         '내 목표/성과 조회',  '/goal',                '나의 목표 관리 및 진척도 확인', 'N', NOW(), 1),
('GOAL_TEAM_READ',    '팀 목표 조회',       '/to/goals',            '팀원 목표 현황 및 진척도 조회 (팀장용)', 'N', NOW(), 1),

-- [평가]
('EVALUATION_MANAGE', '평가 운영 관리',     '/hr/evaluation',       '평가 일정/지표/대상자 설정 및 운영', 'N', NOW(), 1),
('EVALUATION_EXEC',   '평가 수행',          '/evaluation/assignment/response', '할당된 평가(본인/동료/하향) 수행', 'N', NOW(), 1),
('EVALUATION_READ',   '평가 결과 조회',     '/evaluation/response/my/result', '나의 평가 결과 및 리포트 조회', 'N', NOW(), 1),

-- [인사평가/등급]
('GRADING_MANAGE',    '평가 등급 관리',     '/hr/grading/list',     '전사/부서별 평가 등급 산정 및 조정', 'N', NOW(), 1),
('GRADING_READ',      '내 등급 조회',       '/my/grading',          '나의 평가 등급 및 이력 조회', 'N', NOW(), 1),

-- [급여]
('SALARY_MANAGE',     '급여/정산 관리',     '/salary/basic',        '급여 대장 생성, 급여 정산 및 지급 관리', 'N', NOW(), 1),
('SALARY_READ',       '급여 명세서 조회',   '/salary/dashboard',    '나의 급여 명세서 및 연봉 정보 조회', 'N', NOW(), 1),

-- [근태]
('ATTENDANCE_MANAGE', '근태/휴가 관리',     '/attendance/department', '전사/부서 근태 현황 및 휴가/연차 관리', 'N', NOW(), 1),
('ATTENDANCE_READ',   '내 근태/연차 조회',  '/attendance/my-calendar','나의 출퇴근 기록 및 잔여 연차 조회', 'N', NOW(), 1),

-- [전자결재]
('APPROVAL_MANAGE',   '결재 양식 관리',     '/approval/admin',      '전자결재 양식 생성 및 결재선 관리', 'N', NOW(), 1),
('APPROVAL_WRITE',    '결재 기안/상신',     '/approval/create',     '새로운 결재 문서 작성 및 상신', 'N', NOW(), 1),
('APPROVAL_READ',     '결재 문서함',        '/approval/my-documents', '기안/결재한 문서 목록 조회', 'N', NOW(), 1),

-- [콘텐츠/게시판]
('CONTENTS_MANAGE',   '콘텐츠 관리',        '/all/contents',        '사내 콘텐츠/자료실 게시물 관리', 'N', NOW(), 1),
('CONTENTS_READ',     '콘텐츠 조회',        '/contents',            '사내 콘텐츠 열람 및 다운로드', 'N', NOW(), 1);


-- 2. 역할 (Role)
-- com_id = 1, is_system = 'Y'
INSERT IGNORE INTO role (com_id, is_system, role_key, name, is_deleted, created_at, created_by) VALUES
(1, 'Y', 'OWNER', '최고관리자', 'N', NOW(), 1),
(1, 'Y', 'HRTEAM', '인사팀', 'N', NOW(), 1),
(1, 'Y', 'TEAMLEADER', '팀장', 'N', NOW(), 1),
(1, 'Y', 'EMPLOYEE', '사원', 'N', NOW(), 1);


-- 3. 권한 매핑 (RolePermission)
-- 3-1. OWNER: 모든 권한
INSERT IGNORE INTO role_permission (role_id, perm_id, created_at, created_by)
SELECT r.role_id, p.perm_id, NOW(), 1
FROM role r
JOIN permission p ON 1=1 -- 모든 권한
WHERE r.com_id = 1 AND r.role_key = 'OWNER';

-- 3-2. HRTEAM: 인사/총무 관련 관리 권한 + 사원 기본 권한
INSERT IGNORE INTO role_permission (role_id, perm_id, created_at, created_by)
SELECT r.role_id, p.perm_id, NOW(), 1
FROM role r
JOIN permission p ON p.perm_key IN (
    -- 관리 권한
    'USER_MANAGE', 'DEPT_MANAGE', 'NOTICE_MANAGE', 
    'GOAL_MANAGE', 'EVALUATION_MANAGE', 'GRADING_MANAGE',
    'SALARY_MANAGE', 'ATTENDANCE_MANAGE', 'APPROVAL_MANAGE', 'CONTENTS_MANAGE',
    -- 기본 권한
    'MY_PROFILE_READ', 'MY_PROFILE_UPDATE', 'NOTICE_READ', 'POLICY_READ', 'DEPT_READ',
    'GOAL_READ', 'EVALUATION_EXEC', 'EVALUATION_READ', 'GRADING_READ',
    'SALARY_READ', 'ATTENDANCE_READ', 'APPROVAL_WRITE', 'APPROVAL_READ', 'CONTENTS_READ'
)
WHERE r.com_id = 1 AND r.role_key = 'HRTEAM';

-- 3-3. TEAMLEADER: 팀 관리 권한 + 사원 기본 권한
INSERT IGNORE INTO role_permission (role_id, perm_id, created_at, created_by)
SELECT r.role_id, p.perm_id, NOW(), 1
FROM role r
JOIN permission p ON p.perm_key IN (
    -- 팀장 전용
    'GOAL_TEAM_READ', 
    -- 기본 권한
    'MY_PROFILE_READ', 'MY_PROFILE_UPDATE', 'NOTICE_READ', 'POLICY_READ', 'DEPT_READ',
    'GOAL_READ', 'EVALUATION_EXEC', 'EVALUATION_READ', 'GRADING_READ',
    'SALARY_READ', 'ATTENDANCE_READ', 'APPROVAL_WRITE', 'APPROVAL_READ', 'CONTENTS_READ'
)
WHERE r.com_id = 1 AND r.role_key = 'TEAMLEADER';

-- 3-4. EMPLOYEE: 사원 기본 권한
INSERT IGNORE INTO role_permission (role_id, perm_id, created_at, created_by)
SELECT r.role_id, p.perm_id, NOW(), 1
FROM role r
JOIN permission p ON p.perm_key IN (
    'MY_PROFILE_READ', 'MY_PROFILE_UPDATE', 'NOTICE_READ', 'POLICY_READ', 'DEPT_READ',
    'GOAL_READ', 'EVALUATION_EXEC', 'EVALUATION_READ', 'GRADING_READ',
    'SALARY_READ', 'ATTENDANCE_READ', 'APPROVAL_WRITE', 'APPROVAL_READ', 'CONTENTS_READ'
)
WHERE r.com_id = 1 AND r.role_key = 'EMPLOYEE';


-- 4. [TEST] Dummy Admin (admin01)에게 OWNER 부여
INSERT IGNORE INTO role_employee (role_id, emp_id, created_at, created_by)
SELECT r.role_id, 1001 AS emp_id, NOW(), 1
FROM role r
WHERE r.com_id = 1 AND r.role_key = 'OWNER';
