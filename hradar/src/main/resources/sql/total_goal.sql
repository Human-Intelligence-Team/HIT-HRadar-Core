
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
INSERT INTO kpi_detail (
    kpi_id, goal_id,
    kpi_metric_name,
    kpi_start_value, kpi_target_value,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
      -- 팀 KPI (goal 201)
      (1001, 201, '분기 예산 집행률', 0, 100, 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      -- 개인 KPI (goal 203)
      (1002, 203, '예산 집행 완료 건수', 0, 50, 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      -- 개인 KPI (goal 204)
      (1003, 204, '계약 처리 완료 건수', 0, 40, 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      -- 개인 KPI (goal 205)
      (1004, 205, '비용 절감 실행 건수', 0, 30, 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      -- 팀 KPI (goal 208)
      (1005, 208, '운영 비용 절감률', 0, 15, 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      -- 개인 KPI (goal 209)
      (1006, 209, '비용 절감 분석 리포트 제출 수', 0, 10, 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      -- 개인 KPI (goal 210)
      (1007, 210, '재협상 완료 계약 수', 0, 8, 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      -- 개인 KPI (goal 211)
      (1008, 211, '불필요 지출 제거 건수', 0, 12, 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);


INSERT INTO kpi_progress_log (
    kpi_log_id, kpi_id,
    log_date, log_owner_id,
    log_value,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
      -- 개인 KPI 1002 (goal 203)
      (1, 1002, '2026-03-31', 1007, 40, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1),

      -- 개인 KPI 1003 (goal 204)
      (2, 1003, '2026-03-31', 1007, 30, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1),

      -- 개인 KPI 1004 (goal 205)
      (3, 1004, '2026-03-31', 1007, 20, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1);
INSERT INTO kpi_progress_log (
    kpi_log_id, kpi_id,
    log_date, log_owner_id,
    log_value,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
      (4, 1006, '2026-03-31', 1005, 8, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1),

      (5, 1007, '2026-03-31', 1006, 6, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1),

      (6, 1008, '2026-03-31', 1008, 10, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1);


INSERT INTO okr_key_result (
    key_result_id, goal_id,
    key_result_content,
    okr_metric_name,
    key_target_value,
    is_achieved,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
      -- 팀 OKR (goal 202)
      (3001, 202,
       '문서 처리 시간 20% 단축',
       '평균 처리 시간 감소율',
       100,
       'N', 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      -- 개인 OKR (goal 206)
      (3002, 206,
       '전자결재 프로세스 도입',
       '도입 완료율',
       100,
       'N', 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      -- 개인 OKR (goal 207)
      (3003, 207,
       '문서 자동화 템플릿 구축',
       '자동화 적용률',
       100,
       'N', 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);
INSERT INTO okr_key_result (
    key_result_id, goal_id,
    key_result_content,
    okr_metric_name,
    key_target_value,
    is_achieved,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
      -- 팀 OKR (goal 212)
      (3004, 212,
       '부서간 협업 만족도 80점 이상 달성',
       '협업 만족도 점수',
       80,
       'N', 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      -- 개인 OKR (goal 213)
      (3005, 213,
       '협업 프로세스 가이드 문서 작성 및 배포',
       '가이드 적용률',
       100,
       'N', 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      -- 개인 OKR (goal 214)
      (3006, 214,
       '사내 공지 채널 통합 및 운영',
       '공지 확인율',
       95,
       'N', 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);

INSERT INTO okr_progress_log (
    okr_log_id, key_result_id,
    current_progress,
    log_date, log_owner_id,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
      -- 팀 OKR (goal 202 → owner 1001)
      (10, 3001, 75, '2026-03-31', 1001, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1),

      -- 개인 OKR (goal 206 → owner 1007)
      (11, 3002, 60, '2026-03-31', 1007, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1),

      -- 개인 OKR (goal 207 → owner 1007)
      (12, 3003, 80, '2026-03-31', 1007, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1),
      (13, 3004, 70, '2026-03-31', 1001, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1),

      (14, 3005, 65, '2026-03-31', 1009, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1),

      (15, 3006, 85, '2026-03-31', 1010, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1);
INSERT INTO okr_key_result (
    key_result_id, goal_id,
    key_result_content,
    okr_metric_name,
    key_target_value,
    is_achieved,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
      -- 팀 OKR (goal 202)
      (3001, 202,
       '문서 처리 시간 20% 단축',
       '평균 처리 시간 감소율',
       100,
       'N', 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      -- 개인 OKR (goal 206)
      (3002, 206,
       '전자결재 프로세스 도입',
       '도입 완료율',
       100,
       'N', 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      -- 개인 OKR (goal 207)
      (3003, 207,
       '문서 자동화 템플릿 구축',
       '자동화 적용률',
       100,
       'N', 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);
INSERT INTO okr_key_result (
    key_result_id, goal_id,
    key_result_content,
    okr_metric_name,
    key_target_value,
    is_achieved,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
      -- 팀 OKR (goal 212)
      (3004, 212,
       '부서간 협업 만족도 80점 이상 달성',
       '협업 만족도 점수',
       80,
       'N', 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      -- 개인 OKR (goal 213)
      (3005, 213,
       '협업 프로세스 가이드 문서 작성 및 배포',
       '가이드 적용률',
       100,
       'N', 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      -- 개인 OKR (goal 214)
      (3006, 214,
       '사내 공지 채널 통합 및 운영',
       '공지 확인율',
       95,
       'N', 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);

INSERT INTO okr_progress_log (
    okr_log_id, key_result_id,
    current_progress,
    log_date, log_owner_id,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
      -- 팀 OKR (goal 202 → owner 1001)
      (10, 3001, 75, '2026-03-31', 1001, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1),

      -- 개인 OKR (goal 206 → owner 1007)
      (11, 3002, 60, '2026-03-31', 1007, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1),

      -- 개인 OKR (goal 207 → owner 1007)
      (12, 3003, 80, '2026-03-31', 1007, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1),
      (13, 3004, 70, '2026-03-31', 1001, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1),

      (14, 3005, 65, '2026-03-31', 1009, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1),

      (15, 3006, 85, '2026-03-31', 1010, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1);
