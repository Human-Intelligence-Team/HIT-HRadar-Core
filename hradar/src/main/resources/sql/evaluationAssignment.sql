INSERT INTO evaluation_assignment (
    assignment_id, cycle_eval_type_id,
    evaluator_id, evaluatee_id,
    assignment_status, submitted_at,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
-- 동료
(4001, 1001, 1002, 1001, 'SUBMITTED', '2026-03-30 10:00:00', 'N',
 '2026-03-01 09:00:00', '2026-03-30 10:00:00', 1, 1),

-- 하향
(4002, 1003, 1003, 1001, 'SUBMITTED', '2026-03-30 11:00:00', 'N',
 '2026-03-01 09:00:00', '2026-03-30 11:00:00', 1, 1),

-- 상향
(4003, 1002, 9001, 1001, 'SUBMITTED', '2026-03-30 12:00:00', 'N',
 '2026-03-01 09:00:00', '2026-03-30 12:00:00', 1, 1);
INSERT INTO evaluation_assignment (
    assignment_id,
    cycle_eval_type_id,
    evaluator_id,
    evaluatee_id,
    assignment_status,
    submitted_at,
    is_deleted,
    created_at,
    updated_at,
    created_by,
    updated_by
) VALUES
    (4004, 1004, 1001, 1001, 'SUBMITTED', NOW(), 'N', NOW(), NOW(), 1, 1);
