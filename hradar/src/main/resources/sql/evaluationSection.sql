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

/*자기평가 섹션*/
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
INSERT INTO evaluation_section (
    section_id, cycle_eval_type_id,
    section_title, section_description, section_order,
    is_deleted, created_at, updated_at, created_by, updated_by
) VALUES
-- 2. 자기성장
(3002, 1004, '자기성장', '직무를 통한 개인 성장 수준을 평가합니다', 2, 'N', NOW(), NOW(), 1, 1),

-- 3. 업무주도성
(3003, 1004, '업무주도성', '업무에 대한 주도적 태도를 평가합니다', 3, 'N', NOW(), NOW(), 1, 1),

-- 4. 자기회고
(3004, 1004, '자기회고', '본인의 업무를 돌아봅니다', 4, 'N', NOW(), NOW(), 1, 1);