INSERT INTO kpi_detail (
    kpi_id,
    goal_id,
    kpi_metric_name,
    kpi_start_value,
    kpi_target_value,
    is_deleted,
    created_at,
    updated_at
) VALUES
-- Goal 1 : LEVEL 1 / TEAM / KPI
(
    1,
    1,
    '전체 매출 성장률',
    0,
    100,
    'N',
    NOW(),
    NOW()
),

-- Goal 2 : LEVEL 2 / TEAM / KPI
(
    2,
    2,
    '개발 완료 스토리 포인트',
    0,
    500,
    'N',
    NOW(),
    NOW()
),

-- Goal 3 : LEVEL 3 / PERSONAL / KPI
(
    3,
    3,
    'API 평균 응답 시간 감소율',
    0,
    20,
    'N',
    NOW(),
    NOW()
),

-- Goal 6 : 삭제된 Goal의 KPI (소프트 삭제 테스트)
(
    4,
    6,
    '삭제된 KPI 지표',
    0,
    10,
    'N',
    NOW(),
    NOW()
);
