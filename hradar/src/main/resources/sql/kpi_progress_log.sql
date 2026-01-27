INSERT INTO kpi_progress_log (
    kpi_log_id,
    kpi_id,
    log_date,
    log_owner_id,
    log_value,
    is_deleted,
    created_at,
    updated_at
) VALUES

-- KPI 1 : 전체 매출 성장률 (Goal 1)
(
    1,
    1,
    '2026-01-01',
    101,
    20,
    'N',
    NOW(),
    NOW()
),
(
    2,
    1,
    '2026-01-05',
    101,
    15,
    'N',
    NOW(),
    NOW()
),
(
    3,
    1,
    '2026-01-10',
    102,
    25,
    'N',
    NOW(),
    NOW()
),

-- KPI 2 : 개발 완료 스토리 포인트 (Goal 2)
(
    4,
    2,
    '2026-01-03',
    201,
    120,
    'N',
    NOW(),
    NOW()
),
(
    5,
    2,
    '2026-01-08',
    201,
    80,
    'N',
    NOW(),
    NOW()
),

-- KPI 3 : API 평균 응답 시간 감소율 (Goal 3 / PERSONAL)
(
    6,
    3,
    '2026-01-04',
    301,
    5,
    'N',
    NOW(),
    NOW()
),
(
    7,
    3,
    '2026-01-09',
    301,
    7,
    'N',
    NOW(),
    NOW()
),

-- KPI 4 : 삭제된 Goal의 KPI (데이터는 있으나 Goal 조회 시 제외됨)
(
    8,
    4,
    '2026-01-02',
    401,
    3,
    'N',
    NOW(),
    NOW()
);
