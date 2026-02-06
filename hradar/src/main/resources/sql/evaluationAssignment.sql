INSERT INTO evaluation_assignment (
    assignment_id, cycle_eval_type_id,
    evaluator_id, evaluatee_id,
    assignment_status, submitted_at,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
-- 동료 (1008 → 1007)
(1111, 1001, 1008, 1007, 'SUBMITTED', '2026-03-30 10:00:00', 'N',
 '2026-03-01 09:00:00', '2026-03-30 10:00:00', 1, 1),

-- 하향 (1009 → 1007)
(1112, 1003, 1009, 1007, 'SUBMITTED', '2026-03-30 11:00:00', 'N',
 '2026-03-01 09:00:00', '2026-03-30 11:00:00', 1, 1),

-- 상향 (1008 → 1007)
(1113, 1002, 1008, 1007, 'SUBMITTED', '2026-03-30 12:00:00', 'N',
 '2026-03-01 09:00:00', '2026-03-30 12:00:00', 1, 1);

-- 자기평가 (1007 → 1007)
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
    (1114, 1004, 1007, 1007, 'SUBMITTED', NOW(), 'N', NOW(), NOW(), 1, 1);
