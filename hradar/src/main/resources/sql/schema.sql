/*
 * HRadar Schema DDL
 * Generated based on JPA Entities
 */

SET FOREIGN_KEY_CHECKS = 0;

-- Drop tables if they exist
DROP TABLE IF EXISTS role_employee;
DROP TABLE IF EXISTS role_permission;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS permission;
DROP TABLE IF EXISTS user_account;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS company_position;
DROP TABLE IF EXISTS company;
DROP TABLE IF EXISTS company_application;

-- Legacy table (if exists)
DROP TABLE IF EXISTS positions;

SET FOREIGN_KEY_CHECKS = 1;

-- 1. Company Application
CREATE TABLE company_application (
    application_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name        VARCHAR(200) NOT NULL,
    biz_no              VARCHAR(30) NOT NULL,
    company_telephone   VARCHAR(30) NOT NULL,
    address             VARCHAR(255) NOT NULL,
    status              VARCHAR(15) NOT NULL,
    creater_name        VARCHAR(50) NOT NULL,
    creater_email       VARCHAR(100) NOT NULL,
    creater_login_id    VARCHAR(100) NOT NULL,
    reject_reason       VARCHAR(500),
    reviewed_at         DATETIME(6),
    reviewed_by         BIGINT
) ENGINE=InnoDB;

-- 2. Company
CREATE TABLE company (
    com_id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    application_id      BIGINT NOT NULL,
    company_code        VARCHAR(30) NOT NULL UNIQUE,
    name                VARCHAR(100) NOT NULL,
    ceo_name            VARCHAR(50),
    company_email       VARCHAR(100),
    biz_no              VARCHAR(30) NOT NULL UNIQUE,
    address             VARCHAR(255) NOT NULL,
    company_telephone   VARCHAR(30),
    founded_date        DATE,
    status              VARCHAR(15) NOT NULL,
    is_deleted          CHAR(1) DEFAULT 'N' NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT
) ENGINE=InnoDB;

-- 3. Company Position (Positions Entity)
CREATE TABLE company_position (
    position_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    com_id              BIGINT NOT NULL,
    name                VARCHAR(50) NOT NULL,
    `rank`              INT NOT NULL,
    is_deleted          CHAR(1) DEFAULT 'N' NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT,
    CONSTRAINT UK_POSITION_COMPANY_NAME UNIQUE (com_id, name)
) ENGINE=InnoDB;

-- 4. Department
CREATE TABLE department (
    dept_id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    com_id              BIGINT NOT NULL,
    parent_dept_id      BIGINT,
    manager_employee_id BIGINT,
    dept_name           VARCHAR(40) NOT NULL,
    dept_phone          VARCHAR(20),
    is_deleted          CHAR(1) DEFAULT 'N' NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT
) ENGINE=InnoDB;

-- 5. Employee
CREATE TABLE employee (
    emp_id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    com_id              BIGINT NOT NULL,
    dept_id             BIGINT,
    position_id         BIGINT,
    name                VARCHAR(50) NOT NULL,
    employee_no         VARCHAR(100),
    email               VARCHAR(150),
    gender              VARCHAR(10),
    birth               VARCHAR(8),
    hire_date           DATE,
    exit_date           DATE,
    image               VARCHAR(255),
    extension_number    VARCHAR(15),
    phone_number        VARCHAR(15),
    note                VARCHAR(1000),
    type                VARCHAR(20),
    is_deleted          CHAR(1) DEFAULT 'N' NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT,
    CONSTRAINT UK_EMP_COMPANY_EMP_NO UNIQUE (com_id, employee_no),
    CONSTRAINT UK_EMP_COMPANY_EMAIL UNIQUE (com_id, email)
) ENGINE=InnoDB;

-- 6. User Account (Account Entity)
CREATE TABLE user_account (
    account_id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    com_id              BIGINT NOT NULL,
    company_code        VARCHAR(30) NOT NULL,
    employee_id         BIGINT,
    login_id            VARCHAR(50) NOT NULL,
    email               VARCHAR(150),
    password            VARCHAR(150) NOT NULL,
    name                VARCHAR(100) NOT NULL,
    role                VARCHAR(10) NOT NULL,
    status              VARCHAR(15) NOT NULL,
    is_deleted          CHAR(1) DEFAULT 'N' NOT NULL,
    CONSTRAINT UK_COMPANY_LOGINID UNIQUE (com_id, login_id),
    CONSTRAINT UK_ACCOUNT_COMPANY_EMAIL UNIQUE (com_id, email)
) ENGINE=InnoDB;

CREATE INDEX IDX_ACCOUNT_EMP_ID ON user_account (com_id, employee_id);

-- 7. Role
CREATE TABLE role (
    role_id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    com_id              BIGINT NOT NULL,
    is_system           CHAR(1) DEFAULT 'N' NOT NULL,
    role_key            VARCHAR(100),
    name                VARCHAR(255) NOT NULL,
    is_deleted          CHAR(1) DEFAULT 'N' NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT,
    CONSTRAINT UK_ROLE_COM_NAME UNIQUE (com_id, name),
    CONSTRAINT UK_ROLE_COM_ROLE_KEY UNIQUE (com_id, role_key)
) ENGINE=InnoDB;

CREATE INDEX IDX_ROLE_COM_ID ON role (com_id);

-- 8. Permission
CREATE TABLE permission (
    perm_id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_perm_id      BIGINT,
    perm_key            VARCHAR(100) NOT NULL UNIQUE,
    name                VARCHAR(255) NOT NULL,
    route_path          VARCHAR(255),
    description         VARCHAR(255),
    is_deleted          CHAR(1) DEFAULT 'N' NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT
) ENGINE=InnoDB;

-- 9. Role Permission (Join Table)
CREATE TABLE role_permission (
    role_id             BIGINT NOT NULL,
    perm_id             BIGINT NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT,
    PRIMARY KEY (role_id, perm_id),
    CONSTRAINT FK_ROLE_PERM_ROLE FOREIGN KEY (role_id) REFERENCES role (role_id),
    CONSTRAINT FK_ROLE_PERM_PERM FOREIGN KEY (perm_id) REFERENCES permission (perm_id)
) ENGINE=InnoDB;

-- 10. Role Employee (Join Table)
CREATE TABLE role_employee (
    role_emp_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_id             BIGINT NOT NULL,
    emp_id              BIGINT NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT,
    CONSTRAINT UK_ROLE_EMP UNIQUE (role_id, emp_id),
    CONSTRAINT FK_ROLE_EMP_ROLE FOREIGN KEY (role_id) REFERENCES role (role_id)
) ENGINE=InnoDB;

CREATE INDEX IDX_ROLE_EMP_EMP_ID ON role_employee (emp_id);


/* =========================
 * DATA INITIALIZATION
 * ========================= */

-- 1) company_application (application_id=1)
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

-- 2) company (com_id=1, company_code=COMP001, application_id=1)
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

-- 3) company_position (position_id=1~8)
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

-- 4) department (com_id=1)
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

-- 5) employee (com_id=1)
INSERT INTO employee
(emp_id, com_id, dept_id, position_id, name, employee_no, email, gender, `type`,
 hire_date, exit_date, birth, phone_number, extension_number, note, image,
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

-- 6) department manager update
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

-- 7) user_account (com_id=1)
INSERT INTO user_account
(account_id, company_code, com_id, employee_id, login_id, email, `name`, `password`, `role`, `status`, is_deleted)
VALUES
    (1,  'COMP001', 1, 1001, 'admin01',  'admin01@comp001.com',  '대표',        '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (2,  'COMP001', 1, 1002, 'user1002', 'user1002@comp001.com', '인사이사',    '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'admin', 'ACTIVE', 'N'),
    (3,  'COMP001', 1, 1003, 'user1003', 'user1003@comp001.com', '재무이사',    '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (4,  'COMP001', 1, 1004, 'user1004', 'user1004@comp001.com', '기술VP',      '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'user',  'ACTIVE', 'N'),
    (5,  'COMP001', 1, 1005, 'user1005', 'user1005@comp001.com', '백엔드팀장',  '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.', 'admin', 'ACTIVE', 'N'),
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

-- 8) Permissions
INSERT IGNORE INTO permission (perm_key, name, route_path, description, is_deleted, created_at, created_by) VALUES
('USER_MANAGE',       '사용자 관리',        '/employee',            '사원 목록, 입사/퇴사 처리 및 계정 관리', 'N', NOW(), 1),
('DEPT_MANAGE',       '부서/조직 관리',     '/department/manage',   '부서 생성/수정/이동 및 조직도 관리', 'N', NOW(), 1),
('COMPANY_MANAGE',    '회사 정보 관리',     '/company/manage',      '회사 정보 수정 및 사업장 관리', 'N', NOW(), 1),
('ROLE_MANAGE',       '역할/권한 관리',     '/company/roles',       '시스템 권한 및 커스텀 역할 관리', 'N', NOW(), 1),
('NOTICE_MANAGE',     '공지사항 관리',      '/notice',              '공지사항 작성/수정/삭제', 'N', NOW(), 1),
('MY_PROFILE_READ',   '내 정보 조회',       '/my-profile',          '나의 인사 정보 및 프로필 조회', 'N', NOW(), 1),
('MY_PROFILE_UPDATE', '내 정보 수정',       '/my-profile',          '나의 정보 수정 (비밀번호 등)', 'N', NOW(), 1),
('NOTICE_READ',       '공지사항 조회',      '/notice',              '전사 공지사항 열람', 'N', NOW(), 1),
('POLICY_READ',       '규정/정책 조회',     '/policy',              '사내 규정 및 정책 열람', 'N', NOW(), 1),
('DEPT_READ',         '조직도 조회',        '/organization',        '전사 조직도 및 부서원 연락처 조회', 'N', NOW(), 1),
('GOAL_MANAGE',       '성과/목표 관리(HR)', '/hr/goals',            '전사 목표 설정 및 성과 관리 (관리자용)', 'N', NOW(), 1),
('GOAL_READ',         '내 목표/성과 조회',  '/goal',                '나의 목표 관리 및 진척도 확인', 'N', NOW(), 1),
('GOAL_TEAM_READ',    '팀 목표 조회',       '/to/goals',            '팀원 목표 현황 및 진척도 조회 (팀장용)', 'N', NOW(), 1),
('EVALUATION_MANAGE', '평가 운영 관리',     '/hr/evaluation',       '평가 일정/지표/대상자 설정 및 운영', 'N', NOW(), 1),
('EVALUATION_EXEC',   '평가 수행',          '/evaluation/assignment/response', '할당된 평가(본인/동료/하향) 수행', 'N', NOW(), 1),
('EVALUATION_READ',   '평가 결과 조회',     '/evaluation/response/my/result', '나의 평가 결과 및 리포트 조회', 'N', NOW(), 1),
('GRADING_MANAGE',    '평가 등급 관리',     '/hr/grading/list',     '전사/부서별 평가 등급 산정 및 조정', 'N', NOW(), 1),
('GRADING_READ',      '내 등급 조회',       '/my/grading',          '나의 평가 등급 및 이력 조회', 'N', NOW(), 1),
('SALARY_MANAGE',     '급여/정산 관리',     '/salary/basic',        '급여 대장 생성, 급여 정산 및 지급 관리', 'N', NOW(), 1),
('SALARY_READ',       '급여 명세서 조회',   '/salary/dashboard',    '나의 급여 명세서 및 연봉 정보 조회', 'N', NOW(), 1),
('ATTENDANCE_MANAGE', '근태/휴가 관리',     '/attendance/department', '전사/부서 근태 현황 및 휴가/연차 관리', 'N', NOW(), 1),
('ATTENDANCE_READ',   '내 근태/연차 조회',  '/attendance/my-calendar','나의 출퇴근 기록 및 잔여 연차 조회', 'N', NOW(), 1),
('APPROVAL_MANAGE',   '결재 양식 관리',     '/approval/admin',      '전자결재 양식 생성 및 결재선 관리', 'N', NOW(), 1),
('APPROVAL_WRITE',    '결재 기안/상신',     '/approval/create',     '새로운 결재 문서 작성 및 상신', 'N', NOW(), 1),
('APPROVAL_READ',     '결재 문서함',        '/approval/my-documents', '기안/결재한 문서 목록 조회', 'N', NOW(), 1),
('CONTENTS_MANAGE',   '콘텐츠 관리',        '/all/contents',        '사내 콘텐츠/자료실 게시물 관리', 'N', NOW(), 1),
('CONTENTS_READ',     '콘텐츠 조회',        '/contents',            '사내 콘텐츠 열람 및 다운로드', 'N', NOW(), 1);

-- 9) Roles
INSERT IGNORE INTO role (com_id, is_system, role_key, name, is_deleted, created_at, created_by) VALUES
(1, 'Y', 'OWNER', '최고관리자', 'N', NOW(), 1),
(1, 'Y', 'HRTEAM', '인사팀', 'N', NOW(), 1),
(1, 'Y', 'TEAMLEADER', '팀장', 'N', NOW(), 1),
(1, 'Y', 'EMPLOYEE', '사원', 'N', NOW(), 1);

-- 10) Role Permissions
-- OWNER
INSERT IGNORE INTO role_permission (role_id, perm_id, created_at, created_by)
SELECT r.role_id, p.perm_id, NOW(), 1
FROM role r
JOIN permission p ON 1=1
WHERE r.com_id = 1 AND r.role_key = 'OWNER';

-- HRTEAM
INSERT IGNORE INTO role_permission (role_id, perm_id, created_at, created_by)
SELECT r.role_id, p.perm_id, NOW(), 1
FROM role r
JOIN permission p ON p.perm_key IN (
    'USER_MANAGE', 'DEPT_MANAGE', 'NOTICE_MANAGE', 'GOAL_MANAGE', 'EVALUATION_MANAGE', 'GRADING_MANAGE',
    'SALARY_MANAGE', 'ATTENDANCE_MANAGE', 'APPROVAL_MANAGE', 'CONTENTS_MANAGE',
    'MY_PROFILE_READ', 'MY_PROFILE_UPDATE', 'NOTICE_READ', 'POLICY_READ', 'DEPT_READ',
    'GOAL_READ', 'EVALUATION_EXEC', 'EVALUATION_READ', 'GRADING_READ',
    'SALARY_READ', 'ATTENDANCE_READ', 'APPROVAL_WRITE', 'APPROVAL_READ', 'CONTENTS_READ'
)
WHERE r.com_id = 1 AND r.role_key = 'HRTEAM';

-- TEAMLEADER
INSERT IGNORE INTO role_permission (role_id, perm_id, created_at, created_by)
SELECT r.role_id, p.perm_id, NOW(), 1
FROM role r
JOIN permission p ON p.perm_key IN (
    'GOAL_TEAM_READ',
    'MY_PROFILE_READ', 'MY_PROFILE_UPDATE', 'NOTICE_READ', 'POLICY_READ', 'DEPT_READ',
    'GOAL_READ', 'EVALUATION_EXEC', 'EVALUATION_READ', 'GRADING_READ',
    'SALARY_READ', 'ATTENDANCE_READ', 'APPROVAL_WRITE', 'APPROVAL_READ', 'CONTENTS_READ'
)
WHERE r.com_id = 1 AND r.role_key = 'TEAMLEADER';

-- EMPLOYEE
INSERT IGNORE INTO role_permission (role_id, perm_id, created_at, created_by)
SELECT r.role_id, p.perm_id, NOW(), 1
FROM role r
JOIN permission p ON p.perm_key IN (
    'MY_PROFILE_READ', 'MY_PROFILE_UPDATE', 'NOTICE_READ', 'POLICY_READ', 'DEPT_READ',
    'GOAL_READ', 'EVALUATION_EXEC', 'EVALUATION_READ', 'GRADING_READ',
    'SALARY_READ', 'ATTENDANCE_READ', 'APPROVAL_WRITE', 'APPROVAL_READ', 'CONTENTS_READ'
)
WHERE r.com_id = 1 AND r.role_key = 'EMPLOYEE';


-- 11) Assign OWNER Role (user1005 / Backend Lead & user1002 / HR Director & user1001 / CEO)
INSERT IGNORE INTO role_employee (role_id, emp_id, created_at, created_by)
SELECT r.role_id, 1005, NOW(), 1
FROM role r
WHERE r.com_id = 1 AND r.role_key = 'OWNER';

INSERT IGNORE INTO role_employee (role_id, emp_id, created_at, created_by)
SELECT r.role_id, 1002, NOW(), 1
FROM role r
WHERE r.com_id = 1 AND r.role_key = 'OWNER';

INSERT IGNORE INTO role_employee (role_id, emp_id, created_at, created_by)
SELECT r.role_id, 1001, NOW(), 1
FROM role r
WHERE r.com_id = 1 AND r.role_key = 'OWNER';

-- 12) Assign EMPLOYEE Role to ALL users (emp_id 1001 ~ 1020)
-- OWNER도 사원 권한을 가질 수 있으므로 모두 부여합니다.
-- 중복 방지 (INSERT IGNORE)
INSERT IGNORE INTO role_employee (role_id, emp_id, created_at, created_by)
SELECT r.role_id, u.employee_id, NOW(), 1
FROM user_account u
JOIN role r ON r.com_id = u.com_id AND r.role_key = 'EMPLOYEE'
WHERE u.com_id = 1;

COMMIT;
