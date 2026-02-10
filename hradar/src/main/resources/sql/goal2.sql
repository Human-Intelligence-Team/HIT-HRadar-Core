
-- 팀 KPI 목표 (LEVEL_1 → owner 1001)
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
    (201, NULL,
     'LEVEL_1', 'TEAM', 'KPI',
     'Q1 예산 집행 관리', '경영지원본부 분기 예산 집행 목표',
     '2026-01-01', '2026-03-31',
     2001, 1001,
     'WAIT', 'APPROVED',
     NULL,
     'N',
     '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);

-- 팀 OKR 목표 (LEVEL_1 → owner 1001)
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
    (202, NULL,
     'LEVEL_1', 'TEAM', 'OKR',
     'Q1 업무 프로세스 효율화', '내부 운영 프로세스 개선',
     '2026-01-01', '2026-03-31',
     2001, 1001,
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
)
                VALUES
                    (203, 201, 'LEVEL_2', 'PERSONAL', 'KPI',
                     'A 사원 - 예산 집행 관리', NULL,
                     '2026-01-01', '2026-03-31',
                     2001, 1007,
                     'WAIT', 'APPROVED',
                     NULL, 'N',
                     '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

                     (204, 201, 'LEVEL_2', 'PERSONAL', 'KPI',
                      'B 사원 - 계약 관리 효율화', NULL,
                      '2026-01-01', '2026-03-31',
                      2001, 1003,
                      'WAIT', 'APPROVED',
                      NULL, 'N',
                      '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

                     (205, 201, 'LEVEL_2', 'PERSONAL', 'KPI',
                      'C 사원 - 비용 절감 실행', NULL,
                      '2026-01-01', '2026-03-31',
                      2001, 1004,
                      'WAIT', 'APPROVED',
                      NULL, 'N',
                      '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);

-- 개인 OKR 목표들 (LEVEL_2 → owner 1007)
INSERT INTO goal (goal_id, parent_goal_id,
                  goal_depth, goal_scope, goal_type,
                  goal_title, goal_description,
                  goal_start_date, goal_end_date,
                  goal_dept_id, owner_id,
                  goal_status, goal_approve_status,
                  reject_reason,
                  is_deleted,
                  created_at, updated_at, created_by, updated_by) VALUES
                     (206, 202, 'LEVEL_2', 'PERSONAL', 'OKR',
                      'A 사원 - 결재 프로세스 개선', NULL,
                      '2026-01-01', '2026-03-31',
                      2001, 1007,
                      'WAIT', 'APPROVED',
                      NULL, 'N',
                      '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

                     (207, 202, 'LEVEL_2', 'PERSONAL', 'OKR',
                      'B 사원 - 문서 처리 자동화', NULL,
                      '2026-01-01', '2026-03-31',
                      2001, 1003,
                      'WAIT', 'APPROVED',
                      NULL, 'N',
                      '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);

-- 팀 KPI 목표 (LEVEL_1 → owner 1002)
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
    (208, NULL,
     'LEVEL_1', 'TEAM', 'KPI',
     'Q1 운영 비용 절감', '분기 운영 비용 절감 목표',
     '2026-01-01', '2026-03-31',
     2001, 1002,
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
)
VALUES
    (209, 208, 'LEVEL_2', 'PERSONAL', 'KPI',
     '경영사원05 - 비용 분석 및 절감 실행', NULL,
     '2026-01-01', '2026-03-31',
     2001, 1005,
     'WAIT', 'APPROVED',
     NULL, 'N',
     '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

    (210, 208, 'LEVEL_2', 'PERSONAL', 'KPI',
     '경영사원06 - 공급 계약 재협상', NULL,
     '2026-01-01', '2026-03-31',
     2001, 1006,
     'WAIT', 'APPROVED',
     NULL, 'N',
     '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

    (211, 208, 'LEVEL_2', 'PERSONAL', 'KPI',
     '경영사원08 - 불필요 지출 점검', NULL,
     '2026-01-01', '2026-03-31',
     2001, 1008,
     'WAIT', 'APPROVED',
     NULL, 'N',
     '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);
-- 팀 OKR 목표 (LEVEL_1 → owner 1001)
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
    (212, NULL,
     'LEVEL_1', 'TEAM', 'OKR',
     'Q1 조직 커뮤니케이션 개선', '부서 간 협업 및 커뮤니케이션 효율 향상',
     '2026-01-01', '2026-03-31',
     2001, 1001,
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
)
VALUES
    (213, 212, 'LEVEL_2', 'PERSONAL', 'OKR',
     '경영사원09 - 협업 프로세스 정비', NULL,
     '2026-01-01', '2026-03-31',
     2001, 1009,
     'WAIT', 'APPROVED',
     NULL, 'N',
     '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

    (214, 212, 'LEVEL_2', 'PERSONAL', 'OKR',
     '경영사원10 - 사내 공지 체계 개선', NULL,
     '2026-01-01', '2026-03-31',
     2001, 1010,
     'WAIT', 'APPROVED',
     NULL, 'N',
     '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);
