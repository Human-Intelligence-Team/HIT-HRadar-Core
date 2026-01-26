INSERT INTO okr_progress_log (
    okr_log_id,
    key_result_id,
    current_progress,
    log_date,
    log_owner_id,
    is_deleted,
    created_at,
    updated_at
) VALUES

-- KR 1 : 서비스 장애 건수 감소 (Goal 4 / TEAM)
(
    1,
    1,
    30,
    '2026-01-03',
    1001,
    'N',
    NOW(),
    NOW()
),
(
    2,
    1,
    60,
    '2026-01-10',
    1001,
    'N',
    NOW(),
    NOW()
),

-- KR 2 : 고객 만족도 점수 향상 (Goal 4 / TEAM)
(
    3,
    2,
    50,
    '2026-01-05',
    1002,
    'N',
    NOW(),
    NOW()
),
(
    4,
    2,
    80,
    '2026-01-12',
    1002,
    'N',
    NOW(),
    NOW()
),

-- KR 3 : 장애 대응 평균 시간 단축 (Goal 5 / PERSONAL / REJECTED)
(
    5,
    3,
    40,
    '2026-01-06',
    1003,
    'N',
    NOW(),
    NOW()
);
