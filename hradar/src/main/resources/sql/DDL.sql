SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS hradar.role_permission;
DROP TABLE IF EXISTS hradar.role_employee_account;
DROP TABLE IF EXISTS hradar.role_employee;
DROP TABLE IF EXISTS hradar.role;
DROP TABLE IF EXISTS hradar.permission;
DROP TABLE IF EXISTS hradar.notification;
DROP TABLE IF EXISTS hradar.notice_image;
DROP TABLE IF EXISTS hradar.notice_attachment;
DROP TABLE IF EXISTS hradar.notice;
DROP TABLE IF EXISTS hradar.notice_category;
DROP TABLE IF EXISTS hradar.leave_usage;
DROP TABLE IF EXISTS hradar.leave_policy;
DROP TABLE IF EXISTS hradar.leave_grant;
DROP TABLE IF EXISTS hradar.kpi_progress_log;
DROP TABLE IF EXISTS hradar.kpi_detail;
DROP TABLE IF EXISTS hradar.ip_range_policy;
DROP TABLE IF EXISTS hradar.individual_grade;
DROP TABLE IF EXISTS hradar.holiday;
DROP TABLE IF EXISTS hradar.grade_objection;
DROP TABLE IF EXISTS hradar.grade_distribution_rule;
DROP TABLE IF EXISTS hradar.grade_cycle;
DROP TABLE IF EXISTS hradar.grade;
DROP TABLE IF EXISTS hradar.goal;
DROP TABLE IF EXISTS hradar.evaluation_response;
DROP TABLE IF EXISTS hradar.objective_options;
DROP TABLE IF EXISTS hradar.evaluation_question;
DROP TABLE IF EXISTS hradar.evaluation_section;
DROP TABLE IF EXISTS hradar.evaluation_assignment;
DROP TABLE IF EXISTS hradar.evaluation_type;
DROP TABLE IF EXISTS hradar.employee_movement_history;
DROP TABLE IF EXISTS hradar.employee_account;
DROP TABLE IF EXISTS hradar.employee;
DROP TABLE IF EXISTS hradar.emp_leave;
DROP TABLE IF EXISTS hradar.document_chunk;
DROP TABLE IF EXISTS hradar.document;
DROP TABLE IF EXISTS hradar.dept_grade;
DROP TABLE IF EXISTS hradar.department;
DROP TABLE IF EXISTS hradar.cycle_evaluation_type;
DROP TABLE IF EXISTS hradar.cycle;
DROP TABLE IF EXISTS hradar.content_tag;
DROP TABLE IF EXISTS hradar.content;
DROP TABLE IF EXISTS hradar.competency_report_custom_code;
DROP TABLE IF EXISTS hradar.competency_report;
DROP TABLE IF EXISTS hradar.compensation_salary_employee;
DROP TABLE IF EXISTS hradar.company_position;
DROP TABLE IF EXISTS hradar.company_application;
DROP TABLE IF EXISTS hradar.company;
DROP TABLE IF EXISTS hradar.common_code;
DROP TABLE IF EXISTS hradar.code_group;
DROP TABLE IF EXISTS hradar.basic_salary_employee;
DROP TABLE IF EXISTS hradar.attendance_work_plan;
DROP TABLE IF EXISTS hradar.attendance_work_log;
DROP TABLE IF EXISTS hradar.attendance_correction;
DROP TABLE IF EXISTS hradar.attendance_auth_log;
DROP TABLE IF EXISTS hradar.attendance_overtime;
DROP TABLE IF EXISTS hradar.attendance;

DROP TABLE IF EXISTS hradar.approval_reference;
DROP TABLE IF EXISTS hradar.approval_payload;
DROP TABLE IF EXISTS hradar.approval_line_step;
DROP TABLE IF EXISTS hradar.approval_line;
DROP TABLE IF EXISTS hradar.approval_history;
DROP TABLE IF EXISTS hradar.approval_document_type;
DROP TABLE IF EXISTS hradar.approval_document;
DROP TABLE IF EXISTS hradar.approval_comment;
DROP TABLE IF EXISTS hradar.account;
DROP TABLE IF EXISTS hradar.user_account;
DROP TABLE IF EXISTS hradar.tag;
DROP TABLE IF EXISTS hradar.team_grade_distribution_policy;
DROP TABLE IF EXISTS hradar.okr_progress_log;
DROP TABLE IF EXISTS hradar.okr_key_result;
DROP TABLE IF EXISTS hradar.rejection_event;
DROP TABLE IF EXISTS hradar.report_content;

SET FOREIGN_KEY_CHECKS = 1;
create table hradar.account
(
    account_id   bigint auto_increment
        primary key,
    company_code varchar(30)                not null,
    company_id   bigint                     not null,
    email        varchar(50)                not null,
    employee_id  bigint                     null,
    login_id     varchar(50)                not null,
    name         varchar(100)               not null,
    password     varchar(255)               not null,
    role         enum ('admin', 'user')     not null,
    status       enum ('ACTIVE', 'RETIRED') not null,
    constraint UK3y2f224qpfpdjkbrybjtprctj
        unique (company_code),
    constraint UKq0uja26qgu1atulenwup9rxyr
        unique (email)
);

create table hradar.approval_comment
(
    comment_id           bigint auto_increment
        primary key,
    parent_comment_id    bigint           null,
    approval_document_id bigint           not null,
    writer_id            bigint           not null,
    content              varchar(255)     not null,
    is_deleted           char default 'N' not null,
    created_at           datetime(6)      not null,
    updated_at           datetime(6)      null,
    created_by           bigint           null,
    updated_by           bigint           null
);

create table hradar.approval_document
(
    doc_id       bigint auto_increment
        primary key,
    company_id   bigint           not null,
    dept_id      bigint           null,
    writer_id    bigint           not null,
    doc_type     varchar(255)     not null,
    title        varchar(200)     not null,
    content      varchar(2000)    null,
    status       varchar(20)      not null,
    submitted_at datetime(6)      null,
    approved_at  datetime(6)      null,
    is_deleted   char default 'N' not null,
    created_at   datetime(6)      not null,
    updated_at   datetime(6)      null,
    created_by   bigint           null,
    updated_by   bigint           null
);

create table hradar.approval_document_type
(
    type_id             bigint auto_increment
        primary key,
    company_id          bigint                     not null,
    doc_type            varchar(255)               not null,
    name                varchar(255)               not null,
    attendance_category varchar(50) default 'NONE' null,
    overtime_minutes    int         default 0      null,
    is_active           tinyint(1)  default 1      not null,
    is_deleted          char        default 'N'    not null,
    created_at          datetime(6)                not null,
    updated_at          datetime(6)                null,
    created_by          bigint                     null,
    updated_by          bigint                     null
);

create table hradar.approval_history
(
    history_id           bigint auto_increment
        primary key,
    doc_id               bigint           not null,
    actor_id             bigint           not null,
    step_id              bigint           null,
    approval_action_type varchar(50)      not null,
    reason               varchar(255)     null,
    acted_at             datetime(6)      null,
    is_deleted           char default 'N' not null,
    created_at           datetime(6)      not null,
    updated_at           datetime(6)      null,
    created_by           bigint           null,
    updated_by           bigint           null
);

create table hradar.approval_line
(
    line_id    bigint auto_increment
        primary key,
    doc_id     bigint           not null,
    is_deleted char default 'N' not null,
    created_at datetime(6)      not null,
    updated_at datetime(6)      null,
    created_by bigint           null,
    updated_by bigint           null
);

create table hradar.approval_line_step
(
    step_id           bigint auto_increment
        primary key,
    line_id           bigint           not null,
    step_order        int              not null,
    approver_id       bigint           not null,
    proxy_approver_id bigint           null,
    status            varchar(20)      not null,
    acted_at          datetime(6)      null,
    reject_reason     varchar(255)     null,
    is_deleted        char default 'N' not null,
    created_at        datetime(6)      not null,
    updated_at        datetime(6)      null,
    created_by        bigint           null,
    updated_by        bigint           null
);

create table hradar.approval_payload
(
    payload_id bigint auto_increment
        primary key,
    doc_id     bigint                       not null,
    payload    longtext collate utf8mb4_bin not null
        check (json_valid(`payload`))
);

create table hradar.approval_reference
(
    reference_id bigint auto_increment
        primary key,
    ref_emp_id   bigint           not null,
    doc_id       bigint           not null,
    is_deleted   char default 'N' not null,
    created_at   datetime(6)      not null,
    updated_at   datetime(6)      null,
    created_by   bigint           null,
    updated_by   bigint           null
);

create table hradar.attendance
(
    attendance_id bigint auto_increment
        primary key,
    emp_id        bigint           not null,
    work_date     date             not null,
    work_type     varchar(50)      not null,
    status        varchar(20)      not null,
    is_deleted    char default 'N' not null,
    created_at    datetime(6)      not null,
    updated_at    datetime(6)      null,
    created_by    bigint           null,
    updated_by    bigint           null
);

create table hradar.attendance_overtime
(
    overtime_id      bigint auto_increment
        primary key,
    emp_id           bigint      not null,
    doc_id           bigint      not null,
    overtime_date    date        not null,
    overtime_minutes int         not null,
    status           varchar(30) not null,
    is_deleted       char        default 'N' not null,

    created_at       datetime(6) not null,
    updated_at       datetime(6) null,
    created_by       bigint      null,
    updated_by       bigint      null
);


create table hradar.attendance_auth_log
(
    auth_log_id   bigint auto_increment
        primary key,
    created_at    datetime(6)              not null,
    created_by    bigint                   not null,
    updated_at    datetime(6)              null,
    updated_by    bigint                   null,
    attendance_id bigint                   not null,
    auth_result   enum ('SUCCESS', 'FAIL') not null,
    auth_type     varchar(50)              not null,
    ip_address    varchar(45)              not null,
    is_deleted    char                     not null,
    mac_address   varchar(50)              null,
    acted_at      datetime(6)              not null
);

create table hradar.attendance_correction
(
    attendance_correction bigint auto_increment
        primary key,
    created_at            datetime(6)                                not null,
    created_by            bigint                                     not null,
    updated_at            datetime(6)                                null,
    updated_by            bigint                                     null,
    attendance_id         bigint                                     not null,
    type                  enum ('TIME_CHANGE', 'LOCATION_CHANGE')    not null,
    decided_at            datetime(6)                                null,
    decider_emp_id        bigint                                     not null,
    doc_id                bigint                                     not null,
    is_deleted            char                                       not null,
    reason                varchar(255)                               not null,
    reject_reason         varchar(255)                               not null,
    requested_at          datetime(6)                                not null,
    requested_value       varchar(255)                               not null,
    requester_emp_id      bigint                                     not null,
    status                enum ('REQUESTED', 'APPROVED', 'REJECTED') not null,
    work_log_id           bigint                                     not null
);

create table hradar.attendance_work_log
(
    work_log_id    bigint auto_increment
        primary key,
    work_log_type  varchar(20)      not null,
    attendance_id  bigint           not null,
    start_at       datetime(6)      not null,
    end_at         datetime(6)      null,
    worked_minutes int              null,
    location       varchar(100)     not null,
    is_deleted     char default 'N' not null,
    created_at     datetime(6)      not null,
    updated_at     datetime(6)      null,
    created_by     bigint           null,
    updated_by     bigint           null
);

create table hradar.attendance_work_plan
(
    work_plan_id     bigint auto_increment
        primary key,
    emp_id           bigint           not null,
    work_type        varchar(50)      not null,
    location         varchar(50)      not null,
    start_at         datetime(6)      not null,
    end_at           datetime(6)      not null,
    overtime_minutes int              null,
    doc_id           bigint           not null,
    status           varchar(30)      not null,
    is_deleted       char default 'N' not null,
    created_at       datetime(6)      not null,
    updated_at       datetime(6)      null,
    created_by       bigint           null,
    updated_by       bigint           null
);

create table hradar.basic_salary_employee
(
    basic_salary_employee_id bigint auto_increment
        primary key,
    created_at               datetime(6)                                                           not null,
    created_by               bigint                                                                not null,
    updated_at               datetime(6)                                                           null,
    updated_by               bigint                                                                null,
    basic_salary             bigint                                                                not null,
    doc_id                   bigint                                                                not null,
    emp_id                   bigint                                                                not null,
    increase_amount          int                                                                   not null,
    increase_rate            decimal(5, 2)                                                         not null,
    is_deleted               char default 'N'                                                      not null,
    remark                   varchar(100)                                                          null,
    salary_increase_type     enum ('REGULAR', 'PERFORMANCE', 'PROMOTION', 'ADJUSTMENT', 'SPECIAL') not null
);

create table hradar.code_group
(
    group_code        varchar(50)  not null
        primary key,
    created_at        datetime(6)  not null,
    created_by        bigint       not null,
    updated_at        datetime(6)  null,
    updated_by        bigint       null,
    group_description varchar(255) null,
    group_name        varchar(100) not null,
    is_deleted        char         not null
);

create table hradar.common_code
(
    code       varchar(50)       not null
        primary key,
    code_name  varchar(50)       not null,
    is_deleted char              not null,
    lang       enum ('KO', 'EG') not null,
    code_order int               not null,
    group_code varchar(50)       not null,
    constraint FKmhd6umsf3hdfgwr8ukw3551re
        foreign key (group_code) references hradar.code_group (group_code)
);

create table hradar.company
(
    com_id            bigint auto_increment
        primary key,
    application_id    bigint           not null,
    company_code      varchar(30)      not null,
    name              varchar(100)     not null,
    ceo_name          varchar(50)      null,
    company_email     varchar(100)     null,
    biz_no            varchar(30)      not null,
    address           varchar(255)     not null,
    company_telephone varchar(30)      null,
    founded_date      date             null,
    status            varchar(15)      not null,
    is_deleted        char default 'N' not null,
    created_at        datetime(6)      not null,
    updated_at        datetime(6)      null,
    created_by        bigint           null,
    updated_by        bigint           null,
    constraint biz_no
        unique (biz_no),
    constraint company_code
        unique (company_code)
);

create table hradar.company_application
(
    application_id    bigint auto_increment
        primary key,
    company_name      varchar(200) not null,
    biz_no            varchar(30)  not null,
    company_telephone varchar(30)  not null,
    address           varchar(255) not null,
    status            varchar(15)  not null,
    creater_name      varchar(50)  not null,
    creater_email     varchar(100) not null,
    creater_login_id  varchar(100) not null,
    reject_reason     varchar(500) null,
    reviewed_at       datetime(6)  null,
    reviewed_by       bigint       null
);

create table hradar.company_position
(
    position_id bigint auto_increment
        primary key,
    com_id      bigint           not null,
    name        varchar(50)      not null,
    rank        int              not null,
    is_deleted  char default 'N' not null,
    created_at  datetime(6)      not null,
    updated_at  datetime(6)      null,
    created_by  bigint           null,
    updated_by  bigint           null,
    constraint UK_POSITION_COMPANY_NAME
        unique (com_id, name)
);

create table hradar.compensation_salary_employee
(
    compensation_salary_employee_id bigint auto_increment
        primary key,
    created_at                      datetime(6)                                             not null,
    created_by                      bigint                                                  not null,
    updated_at                      datetime(6)                                             null,
    updated_by                      bigint                                                  null,
    amount                          bigint                                                  not null,
    compensation_type               enum ('PERFORMANCE', 'BONUS', 'INCENTIVE', 'ALLOWANCE') not null,
    doc_id                          bigint                                                  not null,
    emp_id                          bigint                                                  not null,
    is_deleted                      char default 'N'                                        not null,
    rate                            decimal(5, 2)                                           not null,
    remark                          varchar(100)                                            null
);

create table hradar.competency_report
(
    competency_report_id   bigint auto_increment
        primary key,
    created_at             datetime(6)      not null,
    created_by             bigint           not null,
    updated_at             datetime(6)      null,
    updated_by             bigint           null,
    cycle_id               bigint           not null,
    emp_id                 bigint           not null,
    end_date               date             not null,
    goal_failure_analysis  varchar(255)     not null,
    is_deleted             char default 'N' not null,
    kpi_okr_result_summary varchar(255)     not null,
    start_date             date             not null
);

create table hradar.competency_report_custom_code
(
    custom_code_id bigint auto_increment
        primary key,
    created_at     datetime(6)      not null,
    created_by     bigint           not null,
    updated_at     datetime(6)      null,
    updated_by     bigint           null,
    com_id         bigint           not null,
    custom_code    varchar(50)      null,
    custom_desc    varchar(255)     null,
    custom_name    varchar(50)      null,
    group_code     varchar(50)      not null,
    group_name     varchar(50)      not null,
    is_deleted     char default 'N' not null,
    sort_order     int              null,
    constraint uk_company_group_custom
        unique (com_id, group_code, custom_code)
);

create index idx_group_code
    on hradar.competency_report_custom_code (com_id, group_code);

create index idx_group_custom
    on hradar.competency_report_custom_code (com_id, group_code, sort_order);

create table hradar.content
(
    content_id    bigint auto_increment
        primary key,
    created_at    datetime(6)      not null,
    created_by    bigint           not null,
    updated_at    datetime(6)      null,
    updated_by    bigint           null,
    is_deleted    char default 'N' not null,
    learning_time int              null,
    level         varchar(255)     null,
    notes         varchar(2000)    null,
    resource_path varchar(3000)    null,
    title         varchar(100)     not null,
    type          varchar(255)     not null,
    company_id    bigint           not null
);

create table hradar.content_tag
(
    content_tag_id bigint auto_increment
        primary key,
    created_at     datetime(6) not null,
    created_by     bigint      not null,
    updated_at     datetime(6) null,
    updated_by     bigint      null,
    content_id     bigint      not null,
    tag_id         bigint      not null
);

create table hradar.cycle
(
    cycle_id                 bigint auto_increment
        primary key,
    created_at               datetime(6)                                         not null,
    created_by               bigint                                              not null,
    updated_at               datetime(6)                                         null,
    updated_by               bigint                                              null,
    company_id               bigint                                              not null,
    cycle_name               varchar(100)                                        not null,
    emp_id                   bigint                                              not null,
    end_date                 datetime(6)                                         not null,
    is_deleted               char                                                not null,
    is_comp_report_generated char                                                not null,
    quarter                  enum ('Q1', 'Q2', 'Q3', 'Q4')                       not null,
    start_date               datetime(6)                                         not null,
    status                   enum ('DRAFT', 'IN_PROGRESS', 'CLOSED', 'APPROVED') not null,
    year                     varchar(4)                                          not null
);

create table hradar.cycle_evaluation_type
(
    cycle_eval_type_id bigint auto_increment
        primary key,
    created_at         datetime(6) not null,
    created_by         bigint      not null,
    updated_at         datetime(6) null,
    updated_by         bigint      null,
    cycle_id           bigint      not null,
    eval_type_id       bigint      not null,
    is_deleted         char        not null
);

create table hradar.department
(
    dept_id             bigint auto_increment
        primary key,
    com_id              bigint           not null,
    parent_dept_id      bigint           null,
    manager_employee_id bigint           null,
    dept_name           varchar(40)      not null,
    dept_phone          varchar(20)      null,
    is_deleted          char default 'N' not null,
    created_at          datetime(6)      not null,
    updated_at          datetime(6)      null,
    created_by          bigint           null,
    updated_by          bigint           null
);

create table hradar.dept_grade
(
    dept_grade_id bigint auto_increment
        primary key,
    created_at    datetime(6)                              not null,
    created_by    bigint                                   not null,
    updated_at    datetime(6)                              null,
    updated_by    bigint                                   null,
    approver_id   bigint                                   null,
    assigner_id   bigint                                   not null,
    cycle_id      bigint                                   not null,
    dept_id       bigint                                   not null,
    grade_id      bigint                                   not null,
    grade_reason  varchar(255)                             not null,
    is_deleted    char                                     not null,
    grade_status  enum ('DRAFT', 'SUBMITTED', 'CONFIRMED') not null
);

create table hradar.document
(
    id         bigint auto_increment
        primary key,
    created_at datetime(6)                 not null,
    created_by bigint                      not null,
    updated_at datetime(6)                 null,
    updated_by bigint                      null,
    category   varchar(255)                null,
    company_id bigint                      null,
    status     enum ('ACTIVE', 'INACTIVE') null,
    title      varchar(255)                null
);

create table hradar.document_chunk
(
    id          bigint auto_increment
        primary key,
    created_at  datetime(6)  not null,
    created_by  bigint       not null,
    updated_at  datetime(6)  null,
    updated_by  bigint       null,
    chunk_index int          not null,
    company_id  bigint       null,
    content     text         null,
    document_id bigint       null,
    section     varchar(255) null
);

create table hradar.emp_leave
(
    leave_id        bigint auto_increment
        primary key,
    doc_id          bigint       not null,
    emp_id          bigint       not null,
    grant_id        bigint       not null,
    leave_type      varchar(255) not null,
    leave_unit_type varchar(255) not null,
    reason          varchar(255) null,
    start_date      date         not null,
    end_date        date         not null,
    leave_days      double       not null,
    requested_at    datetime(6)  not null,
    is_deleted      char         not null,
    created_at      datetime(6)  not null,
    updated_at      datetime(6)  null,
    created_by      bigint       null,
    updated_by      bigint       null
);

create table hradar.employee
(
    emp_id           bigint auto_increment
        primary key,
    com_id           bigint           not null,
    dept_id          bigint           null,
    position_id      bigint           null,
    name             varchar(50)      not null,
    employee_no      varchar(100)     null,
    email            varchar(150)     null,
    gender           varchar(10)      null,
    birth            varchar(8)       null,
    hire_date        date             null,
    exit_date        date             null,
    image            varchar(255)     null,
    extension_number varchar(15)      null,
    phone_number     varchar(15)      null,
    note             varchar(1000)    null,
    type             varchar(20)      null,
    is_deleted       char default 'N' not null,
    created_at       datetime(6)      not null,
    updated_at       datetime(6)      null,
    created_by       bigint           null,
    updated_by       bigint           null,
    constraint UK_EMP_COMPANY_EMAIL
        unique (com_id, email),
    constraint UK_EMP_COMPANY_EMP_NO
        unique (com_id, employee_no)
);

create table hradar.employee_account
(
    emp_account_id bigint      not null
        primary key,
    created_at     datetime(6) not null,
    updated_at     datetime(6) null,
    com_id         bigint      not null,
    emp_id         bigint      not null,
    is_deleted     varchar(1)  not null,
    login_id       varchar(50) not null,
    password_hash  varchar(50) not null,
    role_id        bigint      not null
);

create table hradar.employee_movement_history
(
    movement_id      bigint auto_increment
        primary key,
    created_at       datetime(6)  not null,
    created_by       bigint       not null,
    updated_at       datetime(6)  null,
    updated_by       bigint       null,
    effective_date   date         not null,
    emp_id           bigint       not null,
    event_reason     varchar(255) null,
    from_dept_id     bigint       null,
    emp_acc_role_id  bigint       null,
    from_position_id bigint       null,
    to_dept_id       bigint       null,
    emp_acc_role_id2 bigint       null,
    to_position_id   bigint       null
);

create table hradar.evaluation_assignment
(
    assignment_id      bigint auto_increment
        primary key,
    created_at         datetime(6)                   not null,
    created_by         bigint                        not null,
    updated_at         datetime(6)                   null,
    updated_by         bigint                        null,
    evaluatee_id       bigint                        not null,
    evaluator_id       bigint                        not null,
    is_deleted         char                          not null,
    assignment_status  enum ('PENDING', 'SUBMITTED') not null,
    submitted_at       datetime(6)                   null,
    cycle_eval_type_id bigint                        not null,
    constraint FKs7oxjexoc9xb51k9jt8mv9hrw
        foreign key (cycle_eval_type_id) references hradar.cycle_evaluation_type (cycle_eval_type_id)
);

create table hradar.evaluation_section
(
    section_id          bigint auto_increment
        primary key,
    created_at          datetime(6)  not null,
    created_by          bigint       not null,
    updated_at          datetime(6)  null,
    updated_by          bigint       null,
    is_deleted          char         not null,
    section_description varchar(255) null,
    section_order       int          not null,
    section_title       varchar(200) not null,
    cycle_eval_type_id  bigint       not null,
    constraint FKoa0dpr40yihgq9ciujr3bleh9
        foreign key (cycle_eval_type_id) references hradar.cycle_evaluation_type (cycle_eval_type_id)
);

create table hradar.evaluation_question
(
    question_id      bigint auto_increment
        primary key,
    created_at       datetime(6)                                not null,
    created_by       bigint                                     not null,
    updated_at       datetime(6)                                null,
    updated_by       bigint                                     null,
    question_content varchar(500)                               not null,
    question_type    enum ('OBJECTIVE', 'SUBJECTIVE', 'RATING') not null,
    rating_scale     int                                        null,
    required_status  enum ('REQUIRED', 'OPTIONAL')              not null,
    section_id       bigint                                     not null,
    constraint FKp6cdgc3pcd8cigbs01ks1dx0a
        foreign key (section_id) references hradar.evaluation_section (section_id)
);

create table hradar.evaluation_type
(
    eval_type_id bigint auto_increment
        primary key,
    created_at   datetime(6)  not null,
    created_by   bigint       not null,
    updated_at   datetime(6)  null,
    updated_by   bigint       null,
    company_id   bigint       not null,
    is_deleted   char         not null,
    type_name    varchar(255) not null
);
create table hradar.goal
(
    goal_id             bigint auto_increment
        primary key,
    created_at          datetime(6)                                         not null,
    created_by          bigint                                              not null,
    updated_at          datetime(6)                                         null,
    updated_by          bigint                                              null,
    goal_approve_status enum ('DRAFT', 'SUBMITTED', 'APPROVED', 'REJECTED') not null,
    goal_dept_id        bigint                                              not null,
    goal_depth          enum ('LEVEL_1', 'LEVEL_2', 'LEVEL_3')              not null,
    goal_description    varchar(255)                                        null,
    goal_end_date       date                                                null,
    is_deleted          char                                                not null,
    owner_id            bigint                                              not null,
    parent_goal_id      bigint                                              null,
    goal_status         enum ('WAIT', 'ON_TRACK', 'DONE', 'STOPPED')        not null,
    reject_reason       varchar(500)                                        null,
    goal_scope          enum ('PERSONAL', 'TEAM')                           not null,
    goal_start_date     date                                                null,
    goal_title          varchar(200)                                        null,
    goal_type           enum ('KPI', 'OKR')                                 not null
);

create table hradar.grade
(
    grade_id    bigint auto_increment
        primary key,
    created_at  datetime(6) not null,
    created_by  bigint      not null,
    updated_at  datetime(6) null,
    updated_by  bigint      null,
    com_id      bigint      not null,
    grade_name  varchar(10) not null,
    grade_order int         not null,
    is_deleted  char        not null
);

create table hradar.grade_cycle
(
    grade_cycle_id     bigint auto_increment
        primary key,
    created_at         datetime(6)                          not null,
    updated_at         datetime(6)                          null,
    grade_cycle_name   varchar(100)                         not null,
    period_end_date    date                                 not null,
    period_start_date  date                                 not null,
    grade_cycle_status enum ('CONFIRMED', 'OPEN', 'REVIEW') not null
);

create table hradar.grade_distribution_rule
(
    grade_rule_id bigint auto_increment
        primary key,
    created_at    datetime(6) not null,
    created_by    bigint      not null,
    updated_at    datetime(6) null,
    updated_by    bigint      null,
    grade_id      bigint      not null,
    is_deleted    char        not null,
    max_ratio     int         not null,
    min_ratio     int         not null,
    constraint uk_grade_ratio
        unique (grade_id)
);

create table hradar.grade_objection
(
    objection_id        bigint auto_increment
        primary key,
    created_at          datetime(6)                                not null,
    created_by          bigint                                     not null,
    updated_at          datetime(6)                                null,
    updated_by          bigint                                     null,
    individual_grade_id bigint                                     not null,
    is_deleted          char                                       not null,
    objection_reason    varchar(255)                               not null,
    objection_result    varchar(255)                               null,
    objection_status    enum ('REVIEWING', 'ACCEPTED', 'REJECTED') not null,
    resolved_by         bigint                                     null
);

create table hradar.holiday
(
    holiday_id   bigint auto_increment
        primary key,
    created_at   datetime(6)                                          not null,
    created_by   bigint                                               not null,
    updated_at   datetime(6)                                          null,
    updated_by   bigint                                               null,
    com_id       bigint                                               not null,
    description  varchar(200)                                         null,
    holiday_date date                                                 not null,
    holiday_name varchar(100)                                         not null,
    holiday_type enum ('LEGAL', 'COMPANY', 'TEMPORARY', 'SUBSTITUTE') not null,
    is_deleted   char                                                 not null
);

create table hradar.individual_grade
(
    individual_grade_id bigint auto_increment
        primary key,
    created_at          datetime(6)                              not null,
    created_by          bigint                                   not null,
    updated_at          datetime(6)                              null,
    updated_by          bigint                                   null,
    cycle_id            bigint                                   not null,
    emp_id              bigint                                   not null,
    grade_id            bigint                                   not null,
    grade_reason        varchar(255)                             not null,
    grade_status        enum ('DRAFT', 'SUBMITTED', 'CONFIRMED') not null,
    is_deleted          char                                     not null
);

create table hradar.ip_range_policy
(
    ip_id          bigint auto_increment
        primary key,
    created_at     datetime(6)                not null,
    created_by     bigint                     not null,
    updated_at     datetime(6)                null,
    updated_by     bigint                     null,
    cidr           varchar(255)               not null,
    com_id         bigint                     not null,
    ip_policy_type enum ('ATTENDANCE', 'ETC') not null,
    is_active      bit                        not null,
    is_deleted     char                       not null,
    location_name  varchar(255)               not null
);

create table hradar.kpi_detail
(
    kpi_id           bigint auto_increment
        primary key,
    created_at       datetime(6)    not null,
    created_by       bigint         not null,
    updated_at       datetime(6)    null,
    updated_by       bigint         null,
    is_deleted       char           not null,
    kpi_metric_name  varchar(50)    not null,
    kpi_start_value  decimal(10, 2) not null,
    kpi_target_value decimal(10, 2) not null,
    goal_id          bigint         not null,
    constraint FKamewm4etk8i22fcrqwj75js5g
        foreign key (goal_id) references hradar.goal (goal_id)
);

create table hradar.kpi_progress_log
(
    kpi_log_id   bigint auto_increment
        primary key,
    created_at   datetime(6) not null,
    created_by   bigint      not null,
    updated_at   datetime(6) null,
    updated_by   bigint      null,
    is_deleted   char        not null,
    log_date     date        not null,
    log_owner_id bigint      not null,
    log_value    int         null,
    kpi_id       bigint      not null,
    constraint FKm1ba4mbnucir0nqf3c00apdet
        foreign key (kpi_id) references hradar.kpi_detail (kpi_id)
);

create table hradar.leave_grant
(
    grant_id       bigint auto_increment
        primary key,
    emp_id         bigint           not null,
    year           int              not null,
    total_days     double           not null,
    remaining_days double           not null,
    granted_days   date             null,
    expire_date    date             null,
    is_deleted     char default 'N' null,
    created_at     datetime(6)      not null,
    updated_at     datetime(6)      null,
    created_by     bigint           null,
    updated_by     bigint           null
);

create table hradar.leave_policy
(
    policy_id  bigint auto_increment
        primary key,
    company_id bigint           not null,
    type_code  varchar(255)     not null,
    type_name  varchar(255)     not null,
    unit_code  varchar(255)     not null,
    unit_days  double           not null,
    is_active  char default 'Y' not null,
    is_deleted char default 'N' not null,
    created_at datetime(6)      not null,
    updated_at datetime(6)      null,
    created_by bigint           null,
    updated_by bigint           null
);

create table hradar.leave_usage
(
    usage_id   bigint auto_increment
        primary key,
    grant_id   bigint not null,
    is_deleted char   not null,
    leave_id   bigint not null,
    use_date   date   not null,
    used_days  double not null
);

create table hradar.emp_leave
(
    leave_id        bigint auto_increment
        primary key,
    doc_id          bigint           not null,
    emp_id          bigint           not null,
    grant_id        bigint           not null,
    leave_type      varchar(50)      not null,
    leave_unit_type varchar(50)      not null,
    reason          varchar(255)     null,
    start_date      date             not null,
    end_date        date             not null,
    leave_days      double           not null,
    requested_at    datetime(6)      not null,
    is_deleted      char default 'N' not null,
    created_at      datetime(6)      not null,
    updated_at      datetime(6)      null,
    created_by      bigint           null,
    updated_by      bigint           null
);


create table hradar.notice_attachment
(
    attachment_id bigint auto_increment
        primary key,
    created_at    datetime(6)  not null,
    created_by    bigint       not null,
    updated_at    datetime(6)  null,
    updated_by    bigint       null,
    notice_id     bigint       not null,
    original_name varchar(255) not null,
    stored_name   varchar(255) not null,
    url           varchar(255) not null,
    com_id        bigint       not null,
    is_used       bit          not null
);

create table hradar.notice_category
(
    category_id bigint auto_increment
        primary key,
    created_at  datetime(6)      not null,
    created_by  bigint           not null,
    updated_at  datetime(6)      null,
    updated_by  bigint           null,
    com_id      bigint           not null,
    is_deleted  char default 'N' not null,
    name        varchar(100)     not null
);

create table hradar.notice
(
    notice_id   bigint auto_increment
        primary key,
    created_at  datetime(6)      not null,
    created_by  bigint           not null,
    updated_at  datetime(6)      null,
    updated_by  bigint           null,
    com_id      bigint           not null,
    content     text             not null,
    is_deleted  char default 'N' not null,
    title       varchar(255)     not null,
    category_id bigint           null,
    constraint FKa7i9o4bnwi4ojfour0obewnmi
        foreign key (category_id) references hradar.notice_category (category_id)
);

create table hradar.notice_image
(
    image_id      bigint auto_increment
        primary key,
    created_at    datetime(6)  not null,
    created_by    bigint       not null,
    updated_at    datetime(6)  null,
    updated_by    bigint       null,
    com_id        bigint       not null,
    notice_id     bigint       null,
    original_name varchar(255) not null,
    stored_name   varchar(255) not null,
    url           varchar(255) not null,
    is_used       bit          not null
);

create table hradar.notification
(
    noti_id    bigint       not null
        primary key,
    created_at datetime(6)  not null,
    created_by varchar(50)  not null,
    emp_id     bigint       not null,
    is_read    bit          not null,
    link_url   varchar(255) null,
    message    varchar(255) not null,
    ref_id     bigint       not null,
    title      varchar(100) not null,
    event_id   varchar(100) not null,
    type       varchar(50)  not null,
    user_id    bigint       not null
);
create table hradar.objective_options
(
    option_id      bigint auto_increment
        primary key,
    option_content varchar(200) not null,
    option_score   int          null,
    question_id    bigint       not null,
    constraint FK1be77ha1kqgucenlbfhe05fo0
        foreign key (question_id) references hradar.evaluation_question (question_id)
);

create table hradar.evaluation_response
(
    response_id   bigint auto_increment
        primary key,
    created_at    datetime(6)                                not null,
    created_by    bigint                                     not null,
    updated_at    datetime(6)                                null,
    updated_by    bigint                                     null,
    response_type enum ('OBJECTIVE', 'SUBJECTIVE', 'RATING') not null,
    score         int                                        null,
    text_answer   varchar(2000)                              null,
    assignment_id bigint                                     not null,
    question_id   bigint                                     not null,
    option_id     bigint                                     null,
    constraint FK717dx0eug8j36gmix4iuo0jar
        foreign key (assignment_id) references hradar.evaluation_assignment (assignment_id),
    constraint FKaq2vubuy2giu2o52cfeexfeg2
        foreign key (question_id) references hradar.evaluation_question (question_id),
    constraint FKh9h2in5vi12vnvfy44wopkjrb
        foreign key (option_id) references hradar.objective_options (option_id)
);

create table hradar.okr_key_result
(
    key_result_id      bigint auto_increment
        primary key,
    created_at         datetime(6)     not null,
    created_by         bigint          not null,
    updated_at         datetime(6)     null,
    updated_by         bigint          null,
    key_result_content varchar(300)    not null,
    is_achieved        enum ('Y', 'N') not null,
    is_deleted         char            not null,
    okr_metric_name    varchar(50)     not null,
    key_target_value   int             not null,
    goal_id            bigint          not null,
    constraint FKrtbh2fldskcl04dt4ji3f9u2d
        foreign key (goal_id) references hradar.goal (goal_id)
);

create table hradar.okr_progress_log
(
    okr_log_id       bigint auto_increment
        primary key,
    created_at       datetime(6) not null,
    created_by       bigint      not null,
    updated_at       datetime(6) null,
    updated_by       bigint      null,
    current_progress int         not null,
    is_deleted       char        not null,
    log_date         date        not null,
    log_owner_id     bigint      null,
    key_result_id    bigint      not null,
    constraint FKgn2mfsg1idmlspkfbv3e4cjcb
        foreign key (key_result_id) references hradar.okr_key_result (key_result_id)
);

create table hradar.permission
(
    perm_id        bigint auto_increment
        primary key,
    parent_perm_id bigint           null,
    perm_key       varchar(100)     not null,
    name           varchar(255)     not null,
    route_path     varchar(255)     null,
    description    varchar(255)     null,
    is_deleted     char default 'N' not null,
    created_at     datetime(6)      not null,
    updated_at     datetime(6)      null,
    created_by     bigint           null,
    updated_by     bigint           null,
    constraint perm_key
        unique (perm_key)
);

create table hradar.rejection_event
(
    rejection_id bigint auto_increment
        primary key,
    created_at   datetime(6) not null,
    created_by   bigint      not null,
    updated_at   datetime(6) null,
    updated_by   bigint      null,
    emp_id       bigint      not null,
    rejected_at  datetime(6) not null
);

create table hradar.report_content
(
    report_content_id    bigint auto_increment
        primary key,
    created_at           datetime(6)  not null,
    created_by           bigint       not null,
    updated_at           datetime(6)  null,
    updated_by           bigint       null,
    competency_report_id bigint       not null,
    content_id           bigint       not null,
    reason               varchar(255) null
);

create table hradar.role
(
    role_id    bigint auto_increment
        primary key,
    com_id     bigint           not null,
    is_system  char default 'N' not null,
    role_key   varchar(100)     null,
    name       varchar(255)     not null,
    is_deleted char default 'N' not null,
    created_at datetime(6)      not null,
    updated_at datetime(6)      null,
    created_by bigint           null,
    updated_by bigint           null,
    constraint UK_ROLE_COM_NAME
        unique (com_id, name),
    constraint UK_ROLE_COM_ROLE_KEY
        unique (com_id, role_key)
);

create index IDX_ROLE_COM_ID
    on hradar.role (com_id);

create table hradar.role_employee
(
    role_emp_id bigint auto_increment
        primary key,
    role_id     bigint      not null,
    emp_id      bigint      not null,
    created_at  datetime(6) not null,
    updated_at  datetime(6) null,
    created_by  bigint      null,
    updated_by  bigint      null,
    constraint UK_ROLE_EMP
        unique (role_id, emp_id),
    constraint FK_ROLE_EMP_ROLE
        foreign key (role_id) references hradar.role (role_id)
);

create index IDX_ROLE_EMP_EMP_ID
    on hradar.role_employee (emp_id);

create table hradar.role_employee_account
(
    emp_acc_id     bigint not null
        primary key,
    emp_account_id bigint not null,
    role_id        bigint not null
);

create table hradar.role_permission
(
    role_id    bigint      not null,
    perm_id    bigint      not null,
    created_at datetime(6) not null,
    updated_at datetime(6) null,
    created_by bigint      null,
    updated_by bigint      null,
    primary key (role_id, perm_id),
    constraint FK_ROLE_PERM_PERM
        foreign key (perm_id) references hradar.permission (perm_id),
    constraint FK_ROLE_PERM_ROLE
        foreign key (role_id) references hradar.role (role_id)
);

create table hradar.tag
(
    tag_id     bigint auto_increment
        primary key,
    created_at datetime(6)      not null,
    created_by bigint           not null,
    updated_at datetime(6)      null,
    updated_by bigint           null,
    is_deleted char default 'N' not null,
    tag_name   varchar(50)      not null,
    company_id bigint           not null,
    constraint UK_1r1tyf6uga9k6jwdqnoqwtk2a
        unique (tag_name)
);

create table hradar.team_grade_distribution_policy
(
    policy_id       bigint auto_increment
        primary key,
    created_at      datetime(6) not null,
    created_by      bigint      not null,
    updated_at      datetime(6) null,
    updated_by      bigint      null,
    company_id      bigint      not null,
    is_deleted      char        not null,
    max_ratio       int         not null,
    member_grade_id bigint      not null,
    min_ratio       int         not null,
    team_grade_id   bigint      not null,
    constraint uk_team_member_grade
        unique (company_id, team_grade_id, member_grade_id)
);

create table hradar.user_account
(
    account_id   bigint auto_increment
        primary key,
    com_id       bigint           not null,
    company_code varchar(30)      not null,
    employee_id  bigint           null,
    login_id     varchar(50)      not null,
    email        varchar(150)     null,
    password     varchar(150)     not null,
    name         varchar(100)     not null,
    role         varchar(10)      not null,
    status       varchar(15)      not null,
    is_deleted   char default 'N' not null,
    constraint UK_ACCOUNT_COMPANY_EMAIL
        unique (com_id, email),
    constraint UK_COMPANY_LOGINID
        unique (com_id, login_id)
);

create index IDX_ACCOUNT_EMP_ID
    on hradar.user_account (com_id, employee_id);