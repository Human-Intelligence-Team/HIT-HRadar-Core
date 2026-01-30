-- =========================
-- Department Dummy Data
-- =========================
INSERT INTO department (
    dept_id,
    com_id,
    parent_dept_id,
    manager_employee_id,
    dept_name,
    dept_phone,
    is_deleted,
    created_at,
    created_by,
    updated_at,
    updated_by
) VALUES (
             1,
             1,
             NULL,
             1001,
             '개발팀',
             '02-1234-5678',
             'N',
             NOW(),
             1,
             NOW(),
             1
         );

-- =========================
-- Employee Dummy Data
-- Dept: 개발팀 (dept_id = 1)
-- =========================

INSERT INTO employee (
    emp_id,
    com_id,
    dept_id,
    position_id,
    name,
    employee_no,
    email,
    gender,
    birth,
    hire_date,
    exit_date,
    image,
    extension_number,
    phone_number,
    note,
    type,
    is_deleted,
    created_at,
    created_by,
    updated_at,
    updated_by
) VALUES
-- 1001 : 개발팀 팀장
(1001, 1, 1, 1, '김개발', 'DEV1001', 'dev1001@company.com', 'MALE', '19850101',
 '2010-03-01', NULL, NULL, '101', '010-1000-1001', '개발팀 팀장', 'WORKING', 'N',
 NOW(), 1, NOW(), 1),

-- 1002
(1002, 1, 1, 2, '이백엔드', 'DEV1002', 'dev1002@company.com', 'MALE', '19890214',
 '2015-06-01', NULL, NULL, '102', '010-1000-1002', '백엔드 개발자', 'WORKING', 'N',
 NOW(), 1, NOW(), 1),

-- 1003
(1003, 1, 1, 2, '박프론트', 'DEV1003', 'dev1003@company.com', 'FEMALE', '19910421',
 '2016-09-01', NULL, NULL, '103', '010-1000-1003', '프론트엔드 개발자', 'WORKING', 'N',
 NOW(), 1, NOW(), 1),

-- 1004
(1004, 1, 1, 3, '최인프라', 'DEV1004', 'dev1004@company.com', 'MALE', '19871230',
 '2014-01-10', NULL, NULL, '104', '010-1000-1004', '인프라 담당', 'WORKING', 'N',
 NOW(), 1, NOW(), 1),

-- 1005
(1005, 1, 1, 2, '정모바일', 'DEV1005', 'dev1005@company.com', 'FEMALE', '19930519',
 '2018-07-02', NULL, NULL, '105', '010-1000-1005', '모바일 개발자', 'WORKING', 'N',
 NOW(), 1, NOW(), 1),

-- 1006
(1006, 1, 1, 4, '윤신입', 'DEV1006', 'dev1006@company.com', 'MALE', '19981211',
 '2022-03-02', NULL, NULL, '106', '010-1000-1006', '주니어 개발자', 'WORKING', 'N',
 NOW(), 1, NOW(), 1),

-- 1007
(1007, 1, 1, 4, '한테스트', 'DEV1007', 'dev1007@company.com', 'FEMALE', '19990505',
 '2023-01-02', NULL, NULL, '107', '010-1000-1007', 'QA / 테스트', 'WORKING', 'N',
 NOW(), 1, NOW(), 1);
