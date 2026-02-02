INSERT INTO evaluation_section (
    section_id, cycle_eval_type_id,
    section_title, section_description, section_order,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
      (2001, 1001, '협업', '팀 내 협업 역량 평가', 1, 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (2002, 1002, '협업', '팀 내 협업 역량 평가', 1, 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (2003, 1003, '협업', '팀 내 협업 역량 평가', 1, 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);
INSERT INTO evaluation_section (
    section_id,
    cycle_eval_type_id,
    section_title,
    section_description,
    section_order,
    is_deleted,
    created_at,
    updated_at,
    created_by,
    updated_by
) VALUES
    (3001, 1004, '직무만족도', '본인의 직무 만족도를 평가합니다', 1, 'N', NOW(), NOW(), 1, 1);
