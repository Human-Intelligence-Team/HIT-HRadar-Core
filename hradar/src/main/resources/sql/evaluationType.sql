INSERT INTO evaluation_type (
    eval_type_id, type_name, company_id,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
      (1, '동료평가', 1, 'N', '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (2, '상향평가', 1, 'N', '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (3, '하향평가', 1, 'N', '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);
INSERT INTO evaluation_type (
    eval_type_id,
    type_name,
    company_id,
    is_deleted,
    created_at,
    updated_at,
    created_by,
    updated_by
) VALUES
    (4, '자기평가', 1, 'N', NOW(), NOW(), 1, 1);