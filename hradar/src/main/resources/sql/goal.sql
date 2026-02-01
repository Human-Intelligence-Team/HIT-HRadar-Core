INSERT INTO goal (
    goal_id, parent_goal_id,
    goal_depth, goal_scope, goal_type,
    goal_title, goal_description,
    goal_start_date, goal_end_date,
    goal_dept_id, owner_id,
    goal_status, goal_approve_status,
    reject_reason,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
    (100, NULL,
     'LEVEL_1', 'TEAM', 'KPI',
     'Q1 팀 매출 성장', '팀 전체 매출 목표',
     '2026-01-01', '2026-03-31',
     10, 1,
     'WAIT', 'APPROVED',
     NULL,
     'N',
     '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);
INSERT INTO goal (
    goal_id, parent_goal_id,
    goal_depth, goal_scope, goal_type,
    goal_title, goal_description,
    goal_start_date, goal_end_date,
    goal_dept_id, owner_id,
    goal_status, goal_approve_status,
    reject_reason,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
    (200, NULL,
     'LEVEL_1', 'TEAM', 'OKR',
     'Q1 서비스 안정화', '서비스 품질 향상',
     '2026-01-01', '2026-03-31',
     10, 1,
     'WAIT', 'APPROVED',
     NULL,
     'N',
     '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);
INSERT INTO goal (
    goal_id, parent_goal_id,
    goal_depth, goal_scope, goal_type,
    goal_title, goal_description,
    goal_start_date, goal_end_date,
    goal_dept_id, owner_id,
    goal_status, goal_approve_status,
    reject_reason,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
    (101, 100, 'LEVEL_2', 'PERSONAL', 'KPI',
     'A 사원 - 신규 고객 매출', NULL,
     '2026-01-01', '2026-03-31',
     10, 1,
     'WAIT', 'APPROVED',
     NULL,
     'N',
     '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);

INSERT INTO goal (
    goal_id, parent_goal_id,
    goal_depth, goal_scope, goal_type,
    goal_title, goal_description,
    goal_start_date, goal_end_date,
    goal_dept_id, owner_id,
    goal_status, goal_approve_status,
    reject_reason,
    is_deleted,
    created_at, updated_at, created_by, updated_by
)  VALUES
    (102, 100, 'LEVEL_2', 'PERSONAL', 'KPI',
     'B 사원 - 기존 고객 유지', NULL,
     '2026-01-01', '2026-03-31',
     10, 2,
     'WAIT', 'APPROVED',
     NULL,
     'N',
     '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);

INSERT INTO goal (
    goal_id, parent_goal_id,
    goal_depth, goal_scope, goal_type,
    goal_title, goal_description,
    goal_start_date, goal_end_date,
    goal_dept_id, owner_id,
    goal_status, goal_approve_status,
    reject_reason,
    is_deleted,
    created_at, updated_at, created_by, updated_by
)  VALUES
    (103, 100, 'LEVEL_2', 'PERSONAL', 'KPI',
     'C 사원 - 파트너 매출', NULL,
     '2026-01-01', '2026-03-31',
     10, 3,
     'WAIT', 'APPROVED',
     NULL,
     'N',
     '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);
INSERT INTO goal (
    goal_id, parent_goal_id,
    goal_depth, goal_scope, goal_type,
    goal_title, goal_description,
    goal_start_date, goal_end_date,
    goal_dept_id, owner_id,
    goal_status, goal_approve_status,
    reject_reason,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
    (201, 200, 'LEVEL_2', 'PERSONAL', 'OKR',
    'A 사원 - 장애 감소', NULL,
    '2026-01-01', '2026-03-31',
    10, 1,
    'WAIT', 'APPROVED',
    NULL,
    'N',
    '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);

INSERT INTO goal (
    goal_id, parent_goal_id,
    goal_depth, goal_scope, goal_type,
    goal_title, goal_description,
    goal_start_date, goal_end_date,
    goal_dept_id, owner_id,
    goal_status, goal_approve_status,
    reject_reason,
    is_deleted,
    created_at, updated_at, created_by, updated_by
)VALUES
    (202, 200, 'LEVEL_2', 'PERSONAL', 'OKR',
     'B 사원 - 배포 안정성 향상', NULL,
     '2026-01-01', '2026-03-31',
     10, 2,
     'WAIT', 'APPROVED',
     NULL,
     'N',
     '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);