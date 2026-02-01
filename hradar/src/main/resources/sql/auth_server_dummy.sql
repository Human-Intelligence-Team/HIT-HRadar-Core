-- =========================
-- 인증 서버 (Authentication Server) 더미 데이터
-- 비밀번호: 1234 (BCrypt 해시)
-- 테이블: employee_account
-- =========================

-- BCrypt 해시 값: $2a$10$N9qo8uLOickgx2ZMRZoMye7I8.OMhvLZxLOhZLxLZxLOhZLxLOhZL (예시)
-- 실제 사용 시 BCrypt로 '1234'를 해시한 값으로 교체 필요

-- 개발팀 (emp_id: 1001~1006)
INSERT INTO employee_account (emp_id, company_id, login_id, password, role, initial_password_changed, deleted, created_at, updated_at) VALUES
(1001, 1, 'kimdev', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'HR_ADMIN', false, false, NOW(), NOW()),
(1002, 1, 'leebe', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', false, false, NOW(), NOW()),
(1003, 1, 'dev3', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', false, false, NOW(), NOW()),
(1004, 1, 'dev4', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', false, false, NOW(), NOW()),
(1005, 1, 'dev5', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', false, false, NOW(), NOW()),
(1006, 1, 'dev6', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', false, false, NOW(), NOW());

-- 인사팀 (emp_id: 2001~2005)
INSERT INTO employee_account (emp_id, company_id, login_id, password, role, initial_password_changed, deleted, created_at, updated_at) VALUES
(2001, 1, 'kanghr', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', false, false, NOW(), NOW()),
(2002, 1, 'hr2', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', false, false, NOW(), NOW()),
(2003, 1, 'hr3', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', false, false, NOW(), NOW()),
(2004, 1, 'hr4', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', false, false, NOW(), NOW()),
(2005, 1, 'hr5', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', false, false, NOW(), NOW());

-- 영업팀 (emp_id: 3001~3005)
INSERT INTO employee_account (emp_id, company_id, login_id, password, role, initial_password_changed, deleted, created_at, updated_at) VALUES
(3001, 1, 'shinyoung', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', false, false, NOW(), NOW()),
(3002, 1, 'sales2', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', false, false, NOW(), NOW()),
(3003, 1, 'sales3', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', false, false, NOW(), NOW()),
(3004, 1, 'sales4', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', false, false, NOW(), NOW()),
(3005, 1, 'sales5', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', false, false, NOW(), NOW());
