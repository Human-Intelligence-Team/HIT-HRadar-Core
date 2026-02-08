/* =========================================================
 * HRadar Schema & Seed (DDL + Data)
 * 
 * ✅ 단일 파일로 스키마 생성 및 테스트 데이터 초기화 수행
 * ✅ SET FOREIGN_KEY_CHECKS = 0/1 를 통한 안전한 초기화
 * ========================================================= */

SET FOREIGN_KEY_CHECKS = 0;

-- 1. Drop tables if they exist
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
DROP TABLE IF EXISTS leave_grant;
DROP TABLE IF EXISTS emp_leave;
DROP TABLE IF EXISTS leave_policy;
DROP TABLE IF EXISTS attendance_work_log;
DROP TABLE IF EXISTS attendance_work_plan;
DROP TABLE IF EXISTS attendance;
DROP TABLE IF EXISTS approval_comment;
DROP TABLE IF EXISTS approval_history;
DROP TABLE IF EXISTS approval_line_step;
DROP TABLE IF EXISTS approval_line;
DROP TABLE IF EXISTS approval_reference;
DROP TABLE IF EXISTS approval_payload;
DROP TABLE IF EXISTS approval_document;
DROP TABLE IF EXISTS approval_document_type;
DROP TABLE IF EXISTS positions;

-- 2. Create Tables
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

CREATE TABLE leave_grant (
    grant_id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    emp_id              BIGINT NOT NULL,
    year                INT NOT NULL,
    total_days          DOUBLE NOT NULL,
    remaining_days      DOUBLE NOT NULL,
    granted_days        DATE,
    expire_date         DATE,
    is_deleted          CHAR(1) DEFAULT 'N',
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT
) ENGINE=InnoDB;

CREATE TABLE emp_leave (
    leave_id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    doc_id              BIGINT NOT NULL,
    emp_id              BIGINT NOT NULL,
    grant_id            BIGINT NOT NULL,
    leave_type          VARCHAR(50) NOT NULL,
    leave_unit_type     VARCHAR(50) NOT NULL,
    reason              VARCHAR(255),
    start_date          DATE NOT NULL,
    end_date            DATE NOT NULL,
    leave_days          DOUBLE NOT NULL,
    requested_at        DATETIME(6) NOT NULL,
    is_deleted          CHAR(1) NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT
) ENGINE=InnoDB;

CREATE TABLE leave_policy (
    policy_id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_id          BIGINT NOT NULL,
    type_code           VARCHAR(50) NOT NULL,
    type_name           VARCHAR(100) NOT NULL,
    unit_code           VARCHAR(50) NOT NULL,
    unit_days           DOUBLE NOT NULL,
    is_active           CHAR(1) DEFAULT 'Y' NOT NULL,
    is_deleted          CHAR(1) DEFAULT 'N' NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT
) ENGINE=InnoDB;

CREATE TABLE approval_document_type (
    type_id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_id          BIGINT NOT NULL,
    doc_type            VARCHAR(50) NOT NULL,
    name                VARCHAR(100) NOT NULL,
    attendance_category VARCHAR(50) DEFAULT 'NONE',
    overtime_minutes    INT DEFAULT 0,
    is_active           TINYINT(1) DEFAULT 1 NOT NULL,
    is_deleted          CHAR(1) DEFAULT 'N' NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT
) ENGINE=InnoDB;

CREATE TABLE approval_document (
    doc_id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_id          BIGINT NOT NULL,
    dept_id             BIGINT,
    writer_id           BIGINT NOT NULL,
    doc_type            VARCHAR(50) NOT NULL,
    title               VARCHAR(200) NOT NULL,
    content             VARCHAR(2000),
    status              VARCHAR(20) NOT NULL,
    submitted_at        DATETIME(6),
    approved_at         DATETIME(6),
    is_deleted          CHAR(1) DEFAULT 'N' NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT
) ENGINE=InnoDB;

CREATE TABLE approval_line (
    line_id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    doc_id              BIGINT NOT NULL,
    is_deleted          CHAR(1) DEFAULT 'N' NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT
) ENGINE=InnoDB;

CREATE TABLE approval_line_step (
    step_id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    line_id             BIGINT NOT NULL,
    step_order          INT NOT NULL,
    approver_id         BIGINT NOT NULL,
    proxy_approver_id   BIGINT,
    status              VARCHAR(20) NOT NULL,
    acted_at            DATETIME(6),
    reject_reason       VARCHAR(255),
    is_deleted          CHAR(1) DEFAULT 'N' NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT
) ENGINE=InnoDB;

CREATE TABLE approval_history (
    history_id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    doc_id               BIGINT NOT NULL,
    actor_id             BIGINT NOT NULL,
    step_id              BIGINT,
    approval_action_type VARCHAR(50) NOT NULL,
    reason               VARCHAR(255),
    acted_at             DATETIME(6),
    is_deleted           CHAR(1) DEFAULT 'N' NOT NULL,
    created_at           DATETIME(6) NOT NULL,
    updated_at           DATETIME(6),
    created_by           BIGINT,
    updated_by           BIGINT
) ENGINE=InnoDB;

CREATE TABLE approval_comment (
    comment_id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_comment_id    BIGINT,
    approval_document_id BIGINT NOT NULL,
    writer_id            BIGINT NOT NULL,
    content              VARCHAR(1000) NOT NULL,
    is_deleted           CHAR(1) DEFAULT 'N' NOT NULL,
    created_at           DATETIME(6) NOT NULL,
    updated_at           DATETIME(6),
    created_by           BIGINT,
    updated_by           BIGINT
) ENGINE=InnoDB;

CREATE TABLE approval_reference (
    reference_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    ref_emp_id           BIGINT NOT NULL,
    doc_id               BIGINT NOT NULL,
    is_deleted           CHAR(1) DEFAULT 'N' NOT NULL,
    created_at           DATETIME(6) NOT NULL,
    updated_at           DATETIME(6),
    created_by           BIGINT,
    updated_by           BIGINT
) ENGINE=InnoDB;

CREATE TABLE approval_payload (
    payload_id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    doc_id              BIGINT NOT NULL,
    payload             JSON NOT NULL
) ENGINE=InnoDB;

CREATE TABLE attendance (
    attendance_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    emp_id              BIGINT NOT NULL,
    work_date           DATE NOT NULL,
    work_type           VARCHAR(50) NOT NULL,
    status              VARCHAR(20) NOT NULL,
    is_deleted          CHAR(1) DEFAULT 'N' NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT
) ENGINE=InnoDB;

CREATE TABLE attendance_work_log (
    work_log_id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_log_type       VARCHAR(20) NOT NULL,
    attendance_id       BIGINT NOT NULL,
    start_at            DATETIME(6) NOT NULL,
    end_at              DATETIME(6),
    worked_minutes      INT,
    location            VARCHAR(100) NOT NULL,
    is_deleted          CHAR(1) DEFAULT 'N' NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT
) ENGINE=InnoDB;

CREATE TABLE attendance_work_plan (
    work_plan_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    emp_id              BIGINT NOT NULL,
    work_type           VARCHAR(50) NOT NULL,
    location            VARCHAR(50) NOT NULL,
    start_at            DATETIME(6) NOT NULL,
    end_at              DATETIME(6) NOT NULL,
    overtime_minutes    INT,
    doc_id              BIGINT NOT NULL,
    status              VARCHAR(30) NOT NULL,
    is_deleted          CHAR(1) DEFAULT 'N' NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6),
    created_by          BIGINT,
    updated_by          BIGINT
) ENGINE=InnoDB;

SET FOREIGN_KEY_CHECKS = 1;

START TRANSACTION;

-- 공통 비밀번호(1234) BCrypt 해시 (너가 준 값 그대로)
SET @PW := '$2b$10$ocLiue.qK3S7vygj8IlCyuEGW6JQb0l8dAQ63BAbx795DY9N43NP.';

-- =========================================================
-- 0) 회사 신청서 (application_id=1 고정)
-- =========================================================
INSERT INTO company_application
(application_id, address, biz_no, company_telephone, company_name,
 creater_email, creater_login_id, creater_name, status,
 reject_reason, reviewed_at, reviewed_by)
VALUES
    (1, '서울시 강남구 테헤란로', '123-45-67890', '02-1234-5678', '테스트컴퍼니',
     'admin@hradar.com', 'hr01', '인사팀장01', 'APPROVED',
     NULL, NULL, NULL);

-- =========================================================
-- 1) 회사 (company_code='TEST001'로 앵커, com_id는 AUTO)
-- =========================================================
INSERT INTO company
(application_id, company_code, name, ceo_name, company_email,
 biz_no, address, company_telephone, founded_date,
 status, is_deleted, created_at, updated_at, created_by, updated_by)
VALUES
    (1, 'TEST001', '테스트컴퍼니', '김대표', 'contact@test001.com',
     '123-45-67890', '서울시 강남구 테헤란로', '02-1234-5678', '2018-01-01',
     'APPROVED', 'N', NOW(6), NOW(6), 1, 1);

-- TEST 회사 com_id 확보 (PLATFORM과 절대 섞이지 않게)
SET @TEST_COM_ID := (SELECT com_id FROM company WHERE company_code='TEST001' LIMIT 1);

-- =========================================================
-- 2) 직위 (간단 구조 유지: 6개)
--    position_id는 AUTO이므로 명시하지 않음
-- =========================================================
INSERT INTO company_position (com_id, `rank`, name, is_deleted, created_at, created_by)
VALUES
    (@TEST_COM_ID, 1, '사원',     'N', NOW(6), 1),
    (@TEST_COM_ID, 2, '대리',     'N', NOW(6), 1),
    (@TEST_COM_ID, 3, '과장',     'N', NOW(6), 1),
    (@TEST_COM_ID, 4, '팀장',     'N', NOW(6), 1),
    (@TEST_COM_ID, 5, '임원',     'N', NOW(6), 1),
    (@TEST_COM_ID, 6, '대표이사', 'N', NOW(6), 1);

-- 직위 id 변수(테스트 회사에서 rank로 역조회)
SET @P_STAFF := (SELECT position_id FROM company_position WHERE com_id=@TEST_COM_ID AND `rank`=1 LIMIT 1);
SET @P_ASST  := (SELECT position_id FROM company_position WHERE com_id=@TEST_COM_ID AND `rank`=2 LIMIT 1);
SET @P_MGR   := (SELECT position_id FROM company_position WHERE com_id=@TEST_COM_ID AND `rank`=4 LIMIT 1);

-- =========================================================
-- 3) 부서 4개 (간단 구조 유지: parent/manager NULL)
--    dept_id는 요청대로 명시(2001~2004)
-- =========================================================
INSERT INTO department
(dept_id, com_id, parent_dept_id, manager_employee_id, dept_name, dept_phone,
 is_deleted, created_at, created_by, updated_at, updated_by)
VALUES
    (2001, @TEST_COM_ID, NULL, NULL, '경영지원본부', '02-2001-0000', 'N', NOW(6), 1, NULL, NULL),
    (2002, @TEST_COM_ID, NULL, NULL, '인사팀',       '02-2002-0000', 'N', NOW(6), 1, NULL, NULL),
    (2003, @TEST_COM_ID, NULL, NULL, '개발본부',     '02-2003-0000', 'N', NOW(6), 1, NULL, NULL),
    (2004, @TEST_COM_ID, NULL, NULL, '플랫폼개발팀', '02-2004-0000', 'N', NOW(6), 1, NULL, NULL);

SET @D1 := 2001;
SET @D2 := 2002;
SET @D3 := 2003;
SET @D4 := 2004;

-- =========================================================
-- 4) 직원 40명 (부서별 10명) emp_id=1001~1040 명시
-- =========================================================
INSERT INTO employee
(emp_id, com_id, dept_id, position_id, name, employee_no, email,
 gender, birth, hire_date, `type`,
 phone_number, extension_number, note, image,
 is_deleted, created_at, created_by, updated_at, updated_by)
VALUES
-- 경영지원본부 (1001~1010)
(1001, @TEST_COM_ID, @D1, @P_MGR,   '경영팀장01', 'E1001', 'hq01@test001.com', 'MALE',   '19900101', '2023-01-02', 'WORKING', '010-9000-1001', '1001', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1002, @TEST_COM_ID, @D1, @P_ASST,  '경영대리02', 'E1002', 'hq02@test001.com', 'FEMALE', '19900202', '2023-01-02', 'WORKING', '010-9000-1002', '1002', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1003, @TEST_COM_ID, @D1, @P_STAFF, '경영사원03', 'E1003', 'hq03@test001.com', 'MALE',   '19900303', '2023-01-02', 'WORKING', '010-9000-1003', '1003', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1004, @TEST_COM_ID, @D1, @P_STAFF, '경영사원04', 'E1004', 'hq04@test001.com', 'FEMALE', '19900404', '2023-01-02', 'WORKING', '010-9000-1004', '1004', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1005, @TEST_COM_ID, @D1, @P_STAFF, '경영사원05', 'E1005', 'hq05@test001.com', 'MALE',   '19900505', '2023-01-02', 'WORKING', '010-9000-1005', '1005', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1006, @TEST_COM_ID, @D1, @P_STAFF, '경영사원06', 'E1006', 'hq06@test001.com', 'FEMALE', '19900606', '2023-01-02', 'WORKING', '010-9000-1006', '1006', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1007, @TEST_COM_ID, @D1, @P_STAFF, '경영사원07', 'E1007', 'hq07@test001.com', 'MALE',   '19900707', '2023-01-02', 'WORKING', '010-9000-1007', '1007', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1008, @TEST_COM_ID, @D1, @P_STAFF, '경영사원08', 'E1008', 'hq08@test001.com', 'FEMALE', '19900808', '2023-01-02', 'WORKING', '010-9000-1008', '1008', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1009, @TEST_COM_ID, @D1, @P_STAFF, '경영사원09', 'E1009', 'hq09@test001.com', 'MALE',   '19900909', '2023-01-02', 'WORKING', '010-9000-1009', '1009', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1010, @TEST_COM_ID, @D1, @P_STAFF, '경영사원10', 'E1010', 'hq10@test001.com', 'FEMALE', '19901010', '2023-01-02', 'WORKING', '010-9000-1010', '1010', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),

-- 인사팀 (1011~1020)
(1011, @TEST_COM_ID, @D2, @P_MGR,   '인사팀장01', 'E1011', 'hr01@test001.com', 'FEMALE', '19901111', '2023-02-01', 'WORKING', '010-9000-1011', '1011', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1012, @TEST_COM_ID, @D2, @P_ASST,  '인사대리02', 'E1012', 'hr02@test001.com', 'MALE',   '19901212', '2023-02-01', 'WORKING', '010-9000-1012', '1012', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1013, @TEST_COM_ID, @D2, @P_STAFF, '인사사원03', 'E1013', 'hr03@test001.com', 'FEMALE', '19910113', '2023-02-01', 'WORKING', '010-9000-1013', '1013', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1014, @TEST_COM_ID, @D2, @P_STAFF, '인사사원04', 'E1014', 'hr04@test001.com', 'MALE',   '19910214', '2023-02-01', 'WORKING', '010-9000-1014', '1014', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1015, @TEST_COM_ID, @D2, @P_STAFF, '인사사원05', 'E1015', 'hr05@test001.com', 'FEMALE', '19910315', '2023-02-01', 'WORKING', '010-9000-1015', '1015', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1016, @TEST_COM_ID, @D2, @P_STAFF, '인사사원06', 'E1016', 'hr06@test001.com', 'MALE',   '19910416', '2023-02-01', 'WORKING', '010-9000-1016', '1016', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1017, @TEST_COM_ID, @D2, @P_STAFF, '인사사원07', 'E1017', 'hr07@test001.com', 'FEMALE', '19910517', '2023-02-01', 'WORKING', '010-9000-1017', '1017', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1018, @TEST_COM_ID, @D2, @P_STAFF, '인사사원08', 'E1018', 'hr08@test001.com', 'MALE',   '19910618', '2023-02-01', 'WORKING', '010-9000-1018', '1018', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1019, @TEST_COM_ID, @D2, @P_STAFF, '인사사원09', 'E1019', 'hr09@test001.com', 'FEMALE', '19910719', '2023-02-01', 'WORKING', '010-9000-1019', '1019', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1020, @TEST_COM_ID, @D2, @P_STAFF, '인사사원10', 'E1020', 'hr10@test001.com', 'MALE',   '19910820', '2023-02-01', 'WORKING', '010-9000-1020', '1020', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),

-- 개발본부 (1021~1030)
(1021, @TEST_COM_ID, @D3, @P_MGR,   '개발팀장01', 'E1021', 'dev01@test001.com', 'MALE',   '19910921', '2023-03-01', 'WORKING', '010-9000-1021', '1021', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1022, @TEST_COM_ID, @D3, @P_ASST,  '개발대리02', 'E1022', 'dev02@test001.com', 'FEMALE', '19911022', '2023-03-01', 'WORKING', '010-9000-1022', '1022', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1023, @TEST_COM_ID, @D3, @P_STAFF, '개발사원03', 'E1023', 'dev03@test001.com', 'MALE',   '19911123', '2023-03-01', 'WORKING', '010-9000-1023', '1023', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1024, @TEST_COM_ID, @D3, @P_STAFF, '개발사원04', 'E1024', 'dev04@test001.com', 'FEMALE', '19911224', '2023-03-01', 'WORKING', '010-9000-1024', '1024', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1025, @TEST_COM_ID, @D3, @P_STAFF, '개발사원05', 'E1025', 'dev05@test001.com', 'MALE',   '19920125', '2023-03-01', 'WORKING', '010-9000-1025', '1025', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1026, @TEST_COM_ID, @D3, @P_STAFF, '개발사원06', 'E1026', 'dev06@test001.com', 'FEMALE', '19920226', '2023-03-01', 'WORKING', '010-9000-1026', '1026', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1027, @TEST_COM_ID, @D3, @P_STAFF, '개발사원07', 'E1027', 'dev07@test001.com', 'MALE',   '19920327', '2023-03-01', 'WORKING', '010-9000-1027', '1027', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1028, @TEST_COM_ID, @D3, @P_STAFF, '개발사원08', 'E1028', 'dev08@test001.com', 'FEMALE', '19920428', '2023-03-01', 'WORKING', '010-9000-1028', '1028', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1029, @TEST_COM_ID, @D3, @P_STAFF, '개발사원09', 'E1029', 'dev09@test001.com', 'MALE',   '19920529', '2023-03-01', 'WORKING', '010-9000-1029', '1029', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1030, @TEST_COM_ID, @D3, @P_STAFF, '개발사원10', 'E1030', 'dev10@test001.com', 'FEMALE', '19920630', '2023-03-01', 'WORKING', '010-9000-1030', '1030', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),

-- 플랫폼개발팀 (1031~1040)
(1031, @TEST_COM_ID, @D4, @P_MGR,   '플랫폼팀장01', 'E1031', 'plat01@test001.com', 'FEMALE', '19920701', '2023-04-01', 'WORKING', '010-9000-1031', '1031', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1032, @TEST_COM_ID, @D4, @P_ASST,  '플랫폼대리02', 'E1032', 'plat02@test001.com', 'MALE',   '19920802', '2023-04-01', 'WORKING', '010-9000-1032', '1032', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1033, @TEST_COM_ID, @D4, @P_STAFF, '플랫폼사원03', 'E1033', 'plat03@test001.com', 'FEMALE', '19920903', '2023-04-01', 'WORKING', '010-9000-1033', '1033', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1034, @TEST_COM_ID, @D4, @P_STAFF, '플랫폼사원04', 'E1034', 'plat04@test001.com', 'MALE',   '19921004', '2023-04-01', 'WORKING', '010-9000-1034', '1034', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1035, @TEST_COM_ID, @D4, @P_STAFF, '플랫폼사원05', 'E1035', 'plat05@test001.com', 'FEMALE', '19921105', '2023-04-01', 'WORKING', '010-9000-1035', '1035', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1036, @TEST_COM_ID, @D4, @P_STAFF, '플랫폼사원06', 'E1036', 'plat06@test001.com', 'MALE',   '19921206', '2023-04-01', 'WORKING', '010-9000-1036', '1036', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1037, @TEST_COM_ID, @D4, @P_STAFF, '플랫폼사원07', 'E1037', 'plat07@test001.com', 'FEMALE', '19930107', '2023-04-01', 'WORKING', '010-9000-1037', '1037', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1038, @TEST_COM_ID, @D4, @P_STAFF, '플랫폼사원08', 'E1038', 'plat08@test001.com', 'MALE',   '19930208', '2023-04-01', 'WORKING', '010-9000-1038', '1038', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1039, @TEST_COM_ID, @D4, @P_STAFF, '플랫폼사원09', 'E1039', 'plat09@test001.com', 'FEMALE', '19930309', '2023-04-01', 'WORKING', '010-9000-1039', '1039', NULL, NULL, 'N', NOW(6), 1, NULL, NULL),
(1040, @TEST_COM_ID, @D4, @P_STAFF, '플랫폼사원10', 'E1040', 'plat10@test001.com', 'MALE',   '19930410', '2023-04-01', 'WORKING', '010-9000-1040', '1040', NULL, NULL, 'N', NOW(6), 1, NULL, NULL);

-- =========================================================
-- 5) 계정 40개 (account_id=emp_id, employee_id=emp_id, role='user')
--    login_id 규칙: hq01~hq10, hr01~hr10, dev01~dev10, plat01~plat10
-- =========================================================
INSERT INTO user_account
(account_id, com_id, company_code, employee_id, login_id, email, password, name, role, status, is_deleted
)
SELECT
    e.emp_id AS account_id,
    e.com_id AS com_id,
    'TEST001' AS company_code,
    e.emp_id AS employee_id,
    CONCAT(
            CASE e.dept_id
                WHEN @D1 THEN 'hq'
                WHEN @D2 THEN 'hr'
                WHEN @D3 THEN 'dev'
                WHEN @D4 THEN 'plat'
                END,
            LPAD(((e.emp_id - 1001) % 10) + 1, 2, '0')
    ) AS login_id,
    e.email AS email,
    @PW AS password,
    e.name AS name,
    'user' AS role,
    'ACTIVE' AS status,
    'N' AS is_deleted
FROM employee e
WHERE e.com_id = @TEST_COM_ID
  AND e.emp_id BETWEEN 1001 AND 1040;

-- =========================================================
-- 6) 권한/역할/매핑 (1번 DDL 기준)
-- =========================================================

-- 6-1) permission (너가 준 최신 목록 그대로)
INSERT INTO permission (perm_key, name, route_path, is_deleted, created_at, created_by) VALUES
                                                                                            ('POLICY_READ', '정책 조회', '/policy', 'N', NOW(6), 1),
                                                                                            ('NOTICE_READ', '공지 조회', '/notice', 'N', NOW(6), 1),
                                                                                            ('NOTICE_MANAGE', '공지 관리', '/notice/create', 'N', NOW(6), 1),
                                                                                            ('MY_PROFILE', '내 정보', '/my-profile', 'N', NOW(6), 1),
                                                                                            ('MY_DEPT', '내 부서', '/my-department', 'N', NOW(6), 1),
                                                                                            ('DEPT_LIST', '부서 목록', '/organization', 'N', NOW(6), 1),
                                                                                            ('ORG_CHART', '조직도', '/department/org-chart', 'N', NOW(6), 1),
                                                                                            ('DEPT_MANAGE', '부서 관리', '/department/manage', 'N', NOW(6), 1),
                                                                                            ('EMP_MANAGE', '사원 관리', '/employee', 'N', NOW(6), 1),
                                                                                            ('EMP_LIST_READ', '사원 목록 조회', '/personnel/employees/list', 'N', NOW(6), 1),
                                                                                            ('POS_MANAGE', '직위 관리', '/personnel/positions', 'N', NOW(6), 1),
                                                                                            ('POS_read', '직위 목록', '/personnel/positions/list', 'N', NOW(6), 1),
                                                                                            ('EMP_APPOINT', '인사 발령', '/personnel/appointment', 'N', NOW(6), 1),
                                                                                            ('EMP_HISTORY', '발령 이력', '/personnel/history', 'N', NOW(6), 1),
                                                                                            ('COM_MY', '내 회사', '/company/my', 'N', NOW(6), 1),
                                                                                            ('COM_MANAGE_MY', '회사 관리', '/company/my-manage', 'N', NOW(6), 1),
                                                                                            ('ROLE_MANAGE', '역할 관리', '/company/roles', 'N', NOW(6), 1),
                                                                                            ('COM_MANAGE_ALL', '회사 전체 관리', '/company/manage', 'N', NOW(6), 1),
                                                                                            ('GOAL_LIST', '목표 목록', '/goal', 'N', NOW(6), 1),
                                                                                            ('GOAL_DASH_HR', 'HR 목표 대시보드', '/hr/goals', 'N', NOW(6), 1),
                                                                                            ('GOAL_TEAM_LIST', '팀 목표 목록', '/to/goals', 'N', NOW(6), 1),
                                                                                            ('GOAL_CREATE', '목표 생성', '/goal/create', 'N', NOW(6), 1),
                                                                                            ('REPORT_ALL', '리포트 전체', '/all/competency/report', 'N', NOW(6), 1),
                                                                                            ('REPORT_DEPT', '리포트 부서', '/dept/competency/report', 'N', NOW(6), 1),
                                                                                            ('REPORT_ME', '리포트 개인', '/me/competency/report', 'N', NOW(6), 1),
                                                                                            ('SALARY_DASH', '급여 대시보드', '/salary/dashboard', 'N', NOW(6), 1),
                                                                                            ('SALARY_BASIC_ALL', '기본급 전체', '/all/salary/basic', 'N', NOW(6), 1),
                                                                                            ('SALARY_BASIC_ME', '내 기본급', '/me/salary/basic', 'N', NOW(6), 1),
                                                                                            ('SALARY_COMP_ALL', '변동급 전체', '/all/salary/compensation', 'N', NOW(6), 1),
                                                                                            ('CONTENT_ALL', '콘텐츠 전체', '/all/contents', 'N', NOW(6), 1),
                                                                                            ('TAG_MANAGE', '태그 관리', '/contents/tag', 'N', NOW(6), 1),
                                                                                            ('CYCLE_MANAGE', '회차 관리', '/cycles', 'N', NOW(6), 1),
                                                                                            ('CYCLE_MANAGE_HR', 'HR 회차 관리', '/hr/cycles', 'N', NOW(6), 1),
                                                                                            ('GRADE_SETTING', '등급 설정', '/grade/setting', 'N', NOW(6), 1),
                                                                                            ('GRADE_LIST_DEPT', '부서 등급', '/grading/list', 'N', NOW(6), 1),
                                                                                            ('GRADE_LIST_HR', 'HR 등급', '/hr/grading/list', 'N', NOW(6), 1),
                                                                                            ('GRADE_ASSIGN', '등급 부여', '/to/grading/list', 'N', NOW(6), 1),
                                                                                            ('GRADE_APPROVE', '등급 승인', '/hr/grading/list/approve', 'N', NOW(6), 1),
                                                                                            ('GRADE_MY', '내 등급', '/my/grading', 'N', NOW(6), 1),
                                                                                            ('GRADE_OBJECTION', '이의제기', '/to/grading/objection', 'N', NOW(6), 1),
                                                                                            ('EVAL_TYPE', '평가 유형', '/hr/evaluation/type/setting', 'N', NOW(6), 1),
                                                                                            ('EVAL_FORM', '평가 문항', '/hr/evaluation/question/form/setting', 'N', NOW(6), 1),
                                                                                            ('EVAL_ASSIGN', '평가 배정', '/hr/evaluation/assignment', 'N', NOW(6), 1),
                                                                                            ('EVAL_STATUS', '배정 현황', '/hr/evaluation/assignment/status', 'N', NOW(6), 1),
                                                                                            ('EVAL_EXEC', '평가 수행', '/evaluation/assignment/response', 'N', NOW(6), 1),
                                                                                            ('EVAL_RESULT_HR', '평가 결과(HR)', '/hr/evaluation/response/result', 'N', NOW(6), 1),
                                                                                            ('EVAL_RESULT_MY', '평가 결과(나)', '/evaluation/response/my/result', 'N', NOW(6), 1),
                                                                                            ('DASH_MY', '내 대시보드', '/my/dashboard', 'N', NOW(6), 1),
                                                                                            ('DASH_HR', 'HR 대시보드', '/hr/dashboard', 'N', NOW(6), 1),
                                                                                            ('APPR_TYPE', '결재 양식', '/approval/approval-document-types', 'N', NOW(6), 1),
                                                                                            ('APPR_CREATE', '기안 작성', '/approval/create', 'N', NOW(6), 1),
                                                                                            ('APPR_MY', '내 문서함', '/approval/my-documents', 'N', NOW(6), 1),
                                                                                            ('APPR_ALL', '전체 문서함', '/approval/all-documents', 'N', NOW(6), 1),
                                                                                            ('APPR_ADMIN', '결재 관리', '/approval/admin', 'N', NOW(6), 1),
                                                                                            ('ATT_COMMUTE', '내 출퇴근', '/attendance/commute', 'N', NOW(6), 1),
                                                                                            ('ATT_IP', 'IP 정책', '/attendance/ip-policy', 'N', NOW(6), 1),
                                                                                            ('ATT_DEPT', '부서 근태', '/attendance/department', 'N', NOW(6), 1),
                                                                                            ('ATT_CAL', '근태 캘린더', '/attendance/department-calendar', 'N', NOW(6), 1),
                                                                                            ('LEAVE_MY', '내 휴가', '/leave/my-history', 'N', NOW(6), 1),
                                                                                            ('LEAVE_POLICY', '휴가 정책', '/leave/policy', 'N', NOW(6), 1),
                                                                                            ('LEAVE_DEPT', '부서 휴가', '/leave/admin/department-history', 'N', NOW(6), 1);

-- 6-2) role (role_id 명시: 1~4, com_id는 TEST 회사)
INSERT INTO role (role_id, com_id, is_system, role_key, name, is_deleted, created_at, created_by)
VALUES
    (1, @TEST_COM_ID, 'Y', 'ADMIN',       '최고 관리자', 'N', NOW(6), 1),
    (2, @TEST_COM_ID, 'Y', 'EMPLOYEE',    '일반 사원',   'N', NOW(6), 1),
    (3, @TEST_COM_ID, 'N', 'HR_ADMIN',    '인사 관리자', 'N', NOW(6), 1),
    (4, @TEST_COM_ID, 'N', 'TEAM_LEADER', '부서장',      'N', NOW(6), 1);

-- 6-3) role_permission
-- ADMIN: 모든 권한
INSERT INTO role_permission (role_id, perm_id, created_at, created_by)
SELECT 1, perm_id, NOW(6), 1 FROM permission;

-- EMPLOYEE: 기본 권한
INSERT INTO role_permission (role_id, perm_id, created_at, created_by)
SELECT 2, perm_id, NOW(6), 1 FROM permission WHERE perm_key IN (
                                                                'MY_PROFILE', 'MY_DEPT', 'POLICY_READ', 'NOTICE_READ',
                                                                'ATT_COMMUTE', 'LEAVE_MY', 'APPR_MY', 'APPR_CREATE',
                                                                'DASH_MY', 'SALARY_BASIC_ME', 'EVAL_EXEC', 'EVAL_RESULT_MY',
                                                                'REPORT_ME', 'CONTENT_ALL', 'GRADE_MY', 'GOAL_LIST'
    );

-- HR_ADMIN
INSERT INTO role_permission (role_id, perm_id, created_at, created_by)
SELECT 3, perm_id, NOW(6), 1 FROM permission WHERE perm_key IN (
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
INSERT INTO role_permission (role_id, perm_id, created_at, created_by)
SELECT 4, perm_id, NOW(6), 1 FROM permission WHERE perm_key IN (
                                                                'MY_PROFILE', 'MY_DEPT', 'POLICY_READ', 'NOTICE_READ',
                                                                'ATT_COMMUTE', 'LEAVE_MY', 'APPR_MY', 'APPR_CREATE',
                                                                'GOAL_TEAM_LIST', 'GRADE_LIST_DEPT', 'GRADE_ASSIGN', 'GRADE_OBJECTION',
                                                                'REPORT_DEPT'
    );

-- =========================================================
-- 7) role_employee (사원-역할 매핑)
-- 규칙:
--  - 전원 EMPLOYEE(2)
--  - 각 부서 첫번째(팀장): TEAM_LEADER(4)
--  - 인사팀장(1011): HR_ADMIN(3) 추가
-- =========================================================

-- 전원 EMPLOYEE
INSERT INTO role_employee (role_id, emp_id, created_at, created_by)
SELECT 2, emp_id, NOW(6), 1
FROM employee
WHERE com_id=@TEST_COM_ID AND emp_id BETWEEN 1001 AND 1040;

-- 부서별 팀장 4명 TEAM_LEADER
INSERT INTO role_employee (role_id, emp_id, created_at, created_by) VALUES
                                                                        (4, 1001, NOW(6), 1),
                                                                        (4, 1011, NOW(6), 1),
                                                                        (4, 1021, NOW(6), 1),
                                                                        (4, 1031, NOW(6), 1);

-- 인사팀장에게 HR_ADMIN 추가
INSERT INTO role_employee (role_id, emp_id, created_at, created_by) VALUES
    (1, 1011, NOW(6), 1);

COMMIT;
-- 8) 연차 지급 (전사 사원에게 2026년도 연차 20개씩 지급)
INSERT INTO leave_grant (emp_id, year, total_days, remaining_days, granted_days, expire_date, is_deleted, created_at, created_by)
SELECT emp_id, 2026, 20, 20, '2026-01-01', '2026-12-31', 'N', NOW(6), 1
FROM employee
WHERE com_id = @TEST_COM_ID;

COMMIT;
