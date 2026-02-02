INSERT INTO evaluation_question (
    question_id, section_id,
    question_type, question_content,
    rating_scale, required_status,
    created_at, updated_at, created_by, updated_by
) VALUES
      (3001, 2001, 'RATING', '팀원과 원활하게 소통한다', 5, 'REQUIRED',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (3002, 2001, 'RATING', '갈등 상황에서도 협력적으로 행동한다', 5, 'REQUIRED',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (3003, 2001, 'RATING', '공동 목표 달성에 기여한다', 5, 'REQUIRED',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (3004, 2001, 'RATING', '타인의 의견을 존중한다', 5, 'REQUIRED',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);
INSERT INTO evaluation_question (
    question_id,
    section_id,
    question_type,
    question_content,
    rating_scale,
    required_status,
    created_at,
    updated_at,
    created_by,
    updated_by
) VALUES
      (3005, 3001, 'RATING', '현재 맡은 업무에 전반적으로 만족하고 있다', 5, 'REQUIRED', NOW(), NOW(), 1, 1),
      (3006, 3001, 'RATING', '업무 난이도는 적절하다고 느낀다', 5, 'REQUIRED', NOW(), NOW(), 1, 1),
      (3007, 3001, 'RATING', '현재 직무를 통해 성장하고 있다고 느낀다', 5, 'REQUIRED', NOW(), NOW(), 1, 1),
      (3008, 3001, 'RATING', '업무 환경과 지원에 만족한다', 5, 'REQUIRED', NOW(), NOW(), 1, 1);
