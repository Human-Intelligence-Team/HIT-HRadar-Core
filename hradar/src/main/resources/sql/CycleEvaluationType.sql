INSERT INTO cycle_evaluation_type (
  cycle_eval_type_id, cycle_id, eval_type_id,
  is_deleted,
  created_at, updated_at, created_by, updated_by
) VALUES
(1001, 100, 1, 'N', '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
(1002, 100, 2, 'N', '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
(1003, 100, 3, 'N', '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);
INSERT INTO cycle_evaluation_type (
    cycle_eval_type_id,
    cycle_id,
    eval_type_id,
    is_deleted,
    created_at,
    updated_at,
    created_by,
    updated_by
) VALUES
    (1004, 100, 4, 'N', NOW(), NOW(), 1, 1);
