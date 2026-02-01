/* =========================
 * COMPANY + ORG DUMMY (com_id = 1 / COMP001)
 * company_application -> company -> company_position -> department -> employee -> user_account
 * ========================= */

START TRANSACTION;

-- 0) 기존 더미 삭제 (tenant=1만)
-- FK가 걸려있을 수 있으니 "자식 -> 부모" 순서로 삭제
DELETE FROM user_account        WHERE com_id = 1;
DELETE FROM employee            WHERE com_id = 1;
DELETE FROM department          WHERE com_id = 1;

-- company_position은 com_id로 관리
DELETE FROM company_position    WHERE com_id = 1;

-- (선택) 예전에 쓰던 positions가 남아있다면 같이 삭제
DELETE FROM positions           WHERE company_id = 1;

-- company는 application_id를 들고 있으므로 company 먼저 삭제하면 안됨(보통)
DELETE FROM company             WHERE com_id = 1;
DELETE FROM company_application WHERE application_id = 1;


-- 1) AUTO_INCREMENT 정리(선택)
ALTER TABLE company_application AUTO_INCREMENT = 1;
ALTER TABLE company             AUTO_INCREMENT = 1;
ALTER TABLE company_position    AUTO_INCREMENT = 1;

ALTER TABLE department          AUTO_INCREMENT = 3011;
ALTER TABLE employee            AUTO_INCREMENT = 1001;
ALTER TABLE user_account        AUTO_INCREMENT = 1;


-- 2) company_application (application_id=1)
INSERT INTO company_application
(application_id, address, biz_no, company_telephone, company_name,
 creater_email, creater_login_id, creater_name,
 reject_reason, reviewed_at, reviewed_by, status)
VALUES
    (1,
     '서울특별시 중구 세종대로 110',
     '123-45-67890',
     '02-1000-0000',
     'HIT SIGNAL',
     'admin01@comp001.com',
     'admin01',
     '대표',
     NULL, NULL, NULL,
     'APPROVED'
    );

-- 3) company (com_id=1, company_code=COMP001, application_id=1)
INSERT INTO company
(com_id, created_at, created_by, updated_at, updated_by,
 address, application_id, biz_no, ceo_name, company_code,
 company_email, company_telephone, name, founded_date, is_deleted, status)
VALUES
    (1, NOW(6), 1, NULL, NULL,
     '서울특별시 중구 세종대로 110',
     1,
     '123-45-67890',
     '대표',
     'COMP001',
     'contact@comp001.com',
     '02-1000-0000',
     'HIT SIGNAL',
     '2015-03-01',
     'N',
     'APPROVED'
    );

-- 4) company_position (position_id=1~8)  -> employee.position_id(1~8)와 매칭
INSERT INTO company_position
(position_id, com_id, `rank`, `name`, is_deleted, created_at, created_by, updated_at, updated_by)
VALUES
    (1, 1, 1, 'Intern',   'N', NOW(6), 1, NULL, NULL),
    (2, 1, 2, 'Staff',    'N', NOW(6), 1, NULL, NULL),
    (3, 1, 3, 'Senior',   'N', NOW(6), 1, NULL, NULL),
    (4, 1, 4, 'Lead',     'N', NOW(6), 1, NULL, NULL),
    (5, 1, 5, 'Manager',  'N', NOW(6), 1, NULL, NULL),
    (6, 1, 6, 'Director', 'N', NOW(6), 1, NULL, NULL),
    (7, 1, 7, 'VP',       'N', NOW(6), 1, NULL, NULL),
    (8, 1, 8, 'CEO',      'N', NOW(6), 1, NULL, NULL);



-- 5) department (com_id=1) : 트리 구조 (created_by NOT NULL => 1)
INSERT INTO department
(dept_id, com_id, parent_dept_id, manager_employee_id, dept_name, dept_phone,
 is_deleted, created_at, created_by, updated_at, updated_by)
VALUES
    (3011, 1, NULL, NULL, 'HQ',                 '02-1000-0000', 'N', NOW(6), 1, NULL, NULL),

    (3012, 1, 3011, NULL, 'HR Team',            '02-1000-0100', 'N', NOW(6), 1, NULL, NULL),
    (3013, 1, 3011, NULL, 'Finance Team',       '02-1000-0200', 'N', NOW(6), 1, NULL, NULL),

    (3014, 1, 3011, NULL, 'Engineering',        '02-1000-0300', 'N', NOW(6), 1, NULL, NULL),
    (3015, 1, 3014, NULL, 'Backend Team',       '02-1000-0310', 'N', NOW(6), 1, NULL, NULL),
    (3016, 1, 3014, NULL, 'Frontend Team',      '02-1000-0320', 'N', NOW(6), 1, NULL, NULL),

    (3017, 1, 3011, NULL, 'Sales',              '02-1000-0400', 'N', NOW(6), 1, NULL, NULL),
    (3018, 1, 3017, NULL, 'Domestic Sales',     '02-1000-0410', 'N', NOW(6), 1, NULL, NULL),
    (3019, 1, 3017, NULL, 'Global Sales',       '02-1000-0420', 'N', NOW(6), 1, NULL, NULL),

    (3020, 1, 3011, NULL, 'Operations',         '02-1000-0500', 'N', NOW(6), 1, NULL, NULL);

-- 6) employee (com_id=1) : emp_id 고정
INSERT INTO employee
(emp_id, com_id, dept_id, position_id, name, employee_no, email, gender, `type`,
 hire_date, exit_date, birth,
 phone_number, extension_number, note, image,
 is_deleted, created_at, created_by, updated_at, updated_by)
VALUES
    (1001, 1, 3011, 8, '대표',        'E1001', 'e1001@comp001.com', 'MALE',   'WORKING', '2023-01-02', NULL, '19800101', '010-1000-1001', '1001', 'CEO', NULL, 'N', NOW(6), 1, NULL, NULL),

    (1002, 1, 3012, 6, '인사이사',    'E1002', 'e1002@comp001.com', 'FEMALE', 'WORKING', '2023-02-01', NULL, '19850202', '010-1000-1002', '1002', 'HR Director', NULL, 'N', NOW(6), 1, NULL, NULL),
    (1003, 1, 3013, 6, '재무이사',    'E1003', 'e1003@comp001.com', 'MALE',   'WORKING', '2023-02-01', NULL, '19840303', '010-1000-1003', '1003', 'Finance Director', NULL, 'N', NOW(6), 1, NULL, NULL),

    (1004, 1, 3014, 7, '기술VP',      'E1004', 'e1004@comp001.com', 'MALE',   'WORKING', '2023-03-01', NULL, '19820404', '010-1000-1004', '1004', 'VP Engineering', NULL, 'N', NOW(6), 1, NULL, NULL),

    (1005, 1, 3015, 5, '백엔드팀장',  'E1005', 'e1005@comp001.com', 'MALE',   'WORKING', '2023-03-15', NULL, '19900105', '010-1000-1005', '1005', 'Backend Lead', NULL, 'N', NOW(6), 1, NULL, NULL),
    (1006, 1, 3016, 5, '프론트팀장',  'E1006', 'e1006@comp001.com', 'FEMALE', 'WORKING', '2023-03-15', NULL, '19910206', '010-1000-1006', '1006', 'Frontend Lead', NULL, 'N', NOW(6), 1, NULL, NULL),

    (1007, 1, 3015, 4, '백엔드리드1', 'E1007', 'e1007@comp001.com', 'MALE',   'WORKING', '2023-04-01', NULL, '19930307', '010-1000-1007', '1007', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
    (1008, 1, 3015, 3, '백엔드시니어','E1008', 'e1008@comp001.com', 'FEMALE', 'WORKING', '2023-04-01', NULL, '19940508', '010-1000-1008', '1008', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
    (1009, 1, 3015, 2, '백엔드1',     'E1009', 'e1009@comp001.com', 'MALE',   'WORKING', '2023-04-15', NULL, '19960609', '010-1000-1009', '1009', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
    (1010, 1, 3015, 2, '백엔드2',     'E1010', 'e1010@comp001.com', 'FEMALE', 'LEAVE',   '2023-04-15', NULL, '19970710', '010-1000-1010', '1010', 'On leave', NULL, 'N', NOW(6), 1, NULL, NULL),

    (1011, 1, 3016, 4, '프론트리드1', 'E1011', 'e1011@comp001.com', 'FEMALE', 'WORKING', '2023-04-01', NULL, '19920811', '010-1000-1011', '1011', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
    (1012, 1, 3016, 3, '프론트시니어','E1012', 'e1012@comp001.com', 'MALE',   'WORKING', '2023-04-01', NULL, '19930912', '010-1000-1012', '1012', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
    (1013, 1, 3016, 2, '프론트1',     'E1013', 'e1013@comp001.com', 'FEMALE', 'WORKING', '2023-04-15', NULL, '19941013', '010-1000-1013', '1013', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
    (1014, 1, 3016, 2, '프론트2',     'E1014', 'e1014@comp001.com', 'MALE',   'WORKING', '2023-04-15', NULL, '19951114', '010-1000-1014', '1014', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),

    (1015, 1, 3017, 6, '영업이사',    'E1015', 'e1015@comp001.com', 'MALE',   'WORKING', '2023-05-01', NULL, '19870615', '010-1000-1015', '1015', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
    (1016, 1, 3018, 3, '국내영업1',   'E1016', 'e1016@comp001.com', 'FEMALE', 'WORKING', '2023-05-10', NULL, '19930116', '010-1000-1016', '1016', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
    (1017, 1, 3018, 2, '국내영업2',   'E1017', 'e1017@comp001.com', 'MALE',   'WORKING', '2023-05-10', NULL, '19940217', '010-1000-1017', '1017', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
    (1018, 1, 3019, 3, '해외영업1',   'E1018', 'e1018@comp001.com', 'FEMALE', 'WORKING', '2023-05-10', NULL, '19950318', '010-1000-1018', '1018', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),

    (1019, 1, 3020, 5, '운영매니저',  'E1019', 'e1019@comp001.com', 'MALE',   'WORKING', '2023-06-01', NULL, '19900119', '010-1000-1019', '1019', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
    (1020, 1, 3020, 2, '운영담당',    'E1020', 'e1020@comp001.com', 'FEMALE', 'WORKING', '2023-06-01', NULL, '19910220', '010-1000-1020', '1020', NULL, NULL, 'N', NOW(6), 1, NULL, NULL);

-- 7) department.manager_employee_id 채우기
UPDATE department SET manager_employee_id = 1001 WHERE dept_id = 3011 AND com_id = 1;
UPDATE department SET manager_employee_id = 1002 WHERE dept_id = 3012 AND com_id = 1;
UPDATE department SET manager_employee_id = 1003 WHERE dept_id = 3013 AND com_id = 1;
UPDATE department SET manager_employee_id = 1004 WHERE dept_id = 3014 AND com_id = 1;
UPDATE department SET manager_employee_id = 1005 WHERE dept_id = 3015 AND com_id = 1;
UPDATE department SET manager_employee_id = 1006 WHERE dept_id = 3016 AND com_id = 1;
UPDATE department SET manager_employee_id = 1015 WHERE dept_id = 3017 AND com_id = 1;
UPDATE department SET manager_employee_id = 1016 WHERE dept_id = 3018 AND com_id = 1;
UPDATE department SET manager_employee_id = 1018 WHERE dept_id = 3019 AND com_id = 1;
UPDATE department SET manager_employee_id = 1019 WHERE dept_id = 3020 AND com_id = 1;

-- 8) user_account (com_id=1) : employee 1:1 매핑
-- password: 1234 (BCrypt hash)
INSERT INTO user_account
(account_id, company_code, com_id, employee_id, login_id, email, `name`, `password`, `role`, `status`, is_deleted)
VALUES
    (1,  'COMP001', 1, 1001, 'admin01',  'admin01@comp001.com',  '대표',        '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'admin', 'ACTIVE', 'N'),

    (2,  'COMP001', 1, 1002, 'user1002', 'user1002@comp001.com', '인사이사',    '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (3,  'COMP001', 1, 1003, 'user1003', 'user1003@comp001.com', '재무이사',    '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (4,  'COMP001', 1, 1004, 'user1004', 'user1004@comp001.com', '기술VP',      '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (5,  'COMP001', 1, 1005, 'user1005', 'user1005@comp001.com', '백엔드팀장',  '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (6,  'COMP001', 1, 1006, 'user1006', 'user1006@comp001.com', '프론트팀장',  '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),

    (7,  'COMP001', 1, 1007, 'user1007', 'user1007@comp001.com', '백엔드리드1', '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (8,  'COMP001', 1, 1008, 'user1008', 'user1008@comp001.com', '백엔드시니어','$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (9,  'COMP001', 1, 1009, 'user1009', 'user1009@comp001.com', '백엔드1',     '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (10, 'COMP001', 1, 1010, 'user1010', 'user1010@comp001.com', '백엔드2',     '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),

    (11, 'COMP001', 1, 1011, 'user1011', 'user1011@comp001.com', '프론트리드1', '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (12, 'COMP001', 1, 1012, 'user1012', 'user1012@comp001.com', '프론트시니어','$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (13, 'COMP001', 1, 1013, 'user1013', 'user1013@comp001.com', '프론트1',     '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (14, 'COMP001', 1, 1014, 'user1014', 'user1014@comp001.com', '프론트2',     '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),

    (15, 'COMP001', 1, 1015, 'user1015', 'user1015@comp001.com', '영업이사',    '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (16, 'COMP001', 1, 1016, 'user1016', 'user1016@comp001.com', '국내영업1',   '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (17, 'COMP001', 1, 1017, 'user1017', 'user1017@comp001.com', '국내영업2',   '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (18, 'COMP001', 1, 1018, 'user1018', 'user1018@comp001.com', '해외영업1',   '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (19, 'COMP001', 1, 1019, 'user1019', 'user1019@comp001.com', '운영매니저',  '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (20, 'COMP001', 1, 1020, 'user1020', 'user1020@comp001.com', '운영담당',    '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N');

COMMIT;
