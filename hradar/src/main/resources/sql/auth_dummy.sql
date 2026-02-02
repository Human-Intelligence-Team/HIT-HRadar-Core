-- =========================
-- 인증 서버 (Auth Server) 더미 데이터
-- 비밀번호: 1234 ($2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2)
-- =========================

-- 개발팀 (1001~1006)
INSERT INTO USER_ACCOUNT (user_id, email, password_hash, role, is_deleted, onboarding_status, created_at) VALUES
(1001, 'dev1@company.com', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'HR_ADMIN', 'N', 'DONE', NOW()),
(1002, 'dev2@company.com', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', 'N', 'DONE', NOW()),
(1003, 'dev3@company.com', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', 'N', 'DONE', NOW()),
(1004, 'dev4@company.com', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', 'N', 'DONE', NOW()),
(1005, 'dev5@company.com', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', 'N', 'DONE', NOW()),
(1006, 'dev6@company.com', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', 'N', 'DONE', NOW());

-- 인사팀 (2001~2005)
INSERT INTO USER_ACCOUNT (user_id, email, password_hash, role, is_deleted, onboarding_status, created_at) VALUES
(2001, 'hr1@company.com', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', 'N', 'DONE', NOW()),
(2002, 'hr2@company.com', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', 'N', 'DONE', NOW()),
(2003, 'hr3@company.com', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', 'N', 'DONE', NOW()),
(2004, 'hr4@company.com', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', 'N', 'DONE', NOW()),
(2005, 'hr5@company.com', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', 'N', 'DONE', NOW());

-- 영업팀 (3001~3005)
INSERT INTO USER_ACCOUNT (user_id, email, password_hash, role, is_deleted, onboarding_status, created_at) VALUES
(3001, 'sales1@company.com', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', 'N', 'DONE', NOW()),
(3002, 'sales2@company.com', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', 'N', 'DONE', NOW()),
(3003, 'sales3@company.com', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', 'N', 'DONE', NOW()),
(3004, 'sales4@company.com', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', 'N', 'DONE', NOW()),
(3005, 'sales5@company.com', '$2a$10$7zBv/DkNpKxP7R9M.mJyOuP3l1Z3XvP.9KxG5oYI4B6v2r8XvY5S2', 'EMPLOYEE', 'N', 'DONE', NOW());
