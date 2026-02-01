-- =========================
-- 부서 (Department) 데이터 보강
-- =========================
INSERT INTO department (dept_id, com_id, dept_name, dept_phone, is_deleted, created_at, created_by, updated_at, updated_by) VALUES
(1, 1, '개발팀', '02-123-1001', 'N', NOW(), 1001, NOW(), 1001),
(2, 1, '인사팀', '02-123-1002', 'N', NOW(), 1001, NOW(), 1001),
(3, 1, '영업팀', '02-123-1003', 'N', NOW(), 1001, NOW(), 1001);

-- =========================
-- 사원 (Employee) 데이터 보강
-- =========================

-- 개발팀 (dept_id = 1)
INSERT INTO employee (emp_id, com_id, dept_id, name, employee_no, email, gender, hire_date, type, is_deleted, created_at, created_by, updated_at, updated_by) VALUES
(1001, 1, 1, '김개발', 'DEV001', 'dev1@company.com', 'MALE', '2020-01-01', 'WORKING', 'N', NOW(), 1001, NOW(), 1001),
(1002, 1, 1, '이백엔드', 'DEV002', 'dev2@company.com', 'MALE', '2021-02-01', 'WORKING', 'N', NOW(), 1001, NOW(), 1001),
(1003, 1, 1, '박프론트', 'DEV003', 'dev3@company.com', 'FEMALE', '2022-03-01', 'WORKING', 'N', NOW(), 1001, NOW(), 1001),
(1004, 1, 1, '최인프라', 'DEV004', 'dev4@company.com', 'MALE', '2023-04-01', 'WORKING', 'N', NOW(), 1001, NOW(), 1001),
(1005, 1, 1, '정모바일', 'DEV005', 'dev5@company.com', 'FEMALE', '2024-05-01', 'WORKING', 'N', NOW(), 1001, NOW(), 1001),
(1006, 1, 1, '윤신입', 'DEV006', 'dev6@company.com', 'MALE', '2025-01-01', 'WORKING', 'N', NOW(), 1001, NOW(), 1001);

-- 인사팀 (dept_id = 2)
INSERT INTO employee (emp_id, com_id, dept_id, name, employee_no, email, gender, hire_date, type, is_deleted, created_at, created_by, updated_at, updated_by) VALUES
(2001, 1, 2, '강인사', 'HR001', 'hr1@company.com', 'FEMALE', '2019-05-01', 'WORKING', 'N', NOW(), 1001, NOW(), 1001),
(2002, 1, 2, '조채용', 'HR002', 'hr2@company.com', 'MALE', '2020-06-01', 'WORKING', 'N', NOW(), 1001, NOW(), 1001),
(2003, 1, 2, '유급여', 'HR003', 'hr3@company.com', 'FEMALE', '2021-07-01', 'WORKING', 'N', NOW(), 1001, NOW(), 1001),
(2004, 1, 2, '심노무', 'HR004', 'hr4@company.com', 'MALE', '2022-08-01', 'WORKING', 'N', NOW(), 1001, NOW(), 1001),
(2005, 1, 2, '임복지', 'HR005', 'hr5@company.com', 'FEMALE', '2023-09-01', 'WORKING', 'N', NOW(), 1001, NOW(), 1001);

-- 영업팀 (dept_id = 3)
INSERT INTO employee (emp_id, com_id, dept_id, name, employee_no, email, gender, hire_date, type, is_deleted, created_at, created_by, updated_at, updated_by) VALUES
(3001, 1, 3, '신영업', 'SALES01', 'sales1@company.com', 'MALE', '2018-01-01', 'WORKING', 'N', NOW(), 1001, NOW(), 1001),
(3002, 1, 3, '오매출', 'SALES02', 'sales2@company.com', 'MALE', '2019-02-01', 'WORKING', 'N', NOW(), 1001, NOW(), 1001),
(3003, 1, 3, '권수주', 'SALES03', 'sales3@company.com', 'FEMALE', '2020-03-01', 'WORKING', 'N', NOW(), 1001, NOW(), 1001),
(3004, 1, 3, '안계약', 'SALES04', 'sales4@company.com', 'MALE', '2021-04-01', 'WORKING', 'N', NOW(), 1001, NOW(), 1001),
(3005, 1, 3, '백고객', 'SALES05', 'sales5@company.com', 'FEMALE', '2022-05-01', 'WORKING', 'N', NOW(), 1001, NOW(), 1001);

-- =========================
-- 사용자 계정 (User Account) 데이터 보강
-- =========================

-- 개발팀 계정
INSERT INTO user_account (com_id, company_code, employee_id, login_id, email, password, name, role, status, is_deleted) VALUES
(1, 'HIT', 1001, 'kimdev', 'dev1@company.com', 'password123', '김개발', 'admin', 'ACTIVE', 'N'),
(1, 'HIT', 1002, 'leebe', 'dev2@company.com', 'password123', '이백엔드', 'user', 'ACTIVE', 'N'),
(1, 'HIT', 1003, 'parkfe', 'dev3@company.com', 'password123', '박프론트', 'user', 'ACTIVE', 'N'),
(1, 'HIT', 1004, 'choiinfra', 'dev4@company.com', 'password123', '최인프라', 'user', 'ACTIVE', 'N'),
(1, 'HIT', 1005, 'jungmob', 'dev5@company.com', 'password123', '정모바일', 'user', 'ACTIVE', 'N'),
(1, 'HIT', 1006, 'yoonnew', 'dev6@company.com', 'password123', '윤신입', 'user', 'ACTIVE', 'N');

-- 인사팀 계정
INSERT INTO user_account (com_id, company_code, employee_id, login_id, email, password, name, role, status, is_deleted) VALUES
(1, 'HIT', 2001, 'kanghr', 'hr1@company.com', 'password123', '강인사', 'user', 'ACTIVE', 'N'),
(1, 'HIT', 2002, 'jorec', 'hr2@company.com', 'password123', '조채용', 'user', 'ACTIVE', 'N'),
(1, 'HIT', 2003, 'yupay', 'hr3@company.com', 'password123', '유급여', 'user', 'ACTIVE', 'N'),
(1, 'HIT', 2004, 'simlab', 'hr4@company.com', 'password123', '심노무', 'user', 'ACTIVE', 'N'),
(1, 'HIT', 2005, 'limwel', 'hr5@company.com', 'password123', '임복지', 'user', 'ACTIVE', 'N');

-- 영업팀 계정
INSERT INTO user_account (com_id, company_code, employee_id, login_id, email, password, name, role, status, is_deleted) VALUES
(1, 'HIT', 3001, 'shinyoung', 'sales1@company.com', 'password123', '신영업', 'user', 'ACTIVE', 'N'),
(1, 'HIT', 3002, 'oms', 'sales2@company.com', 'password123', '오매출', 'user', 'ACTIVE', 'N'),
(1, 'HIT', 3003, 'kwonsj', 'sales3@company.com', 'password123', '권수주', 'user', 'ACTIVE', 'N'),
(1, 'HIT', 3004, 'ahnsign', 'sales4@company.com', 'password123', '안계약', 'user', 'ACTIVE', 'N'),
(1, 'HIT', 3005, 'baekcust', 'sales5@company.com', 'password123', '백고객', 'user', 'ACTIVE', 'N');
