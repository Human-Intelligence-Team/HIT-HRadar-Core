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

/*자기 평가*/
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

INSERT INTO evaluation_question (
    question_id, section_id,
    question_type, question_content,
    rating_scale, required_status,
    created_at, updated_at, created_by, updated_by
) VALUES
-- 자기성장
(3010, 3002, 'RATING', '현재 직무를 통해 전문성이 향상되고 있다고 느낀다', 5, 'REQUIRED', NOW(), NOW(), 1, 1),
(3011, 3002, 'RATING', '새로운 업무나 기술에 적극적으로 도전하고 있다', 5, 'REQUIRED', NOW(), NOW(), 1, 1),

-- 업무주도성
(3020, 3003, 'RATING', '업무를 스스로 계획하고 책임감 있게 수행한다', 5, 'REQUIRED', NOW(), NOW(), 1, 1),
(3021, 3003, 'RATING', '문제가 발생했을 때 주도적으로 해결하려 노력한다', 5, 'REQUIRED', NOW(), NOW(), 1, 1);

INSERT INTO evaluation_question (
    question_id, section_id,
    question_type, question_content,
    rating_scale, required_status,
    created_at, updated_at, created_by, updated_by
) VALUES
      (3040, 3001, 'OBJECTIVE', '현재 업무량은 어느 수준이라고 느끼시나요?', NULL, 'REQUIRED', NOW(), NOW(), 1, 1),
      (3041, 3001, 'OBJECTIVE', '최근 업무에 얼마나 몰입하고 있나요?', NULL, 'REQUIRED', NOW(), NOW(), 1, 1),
      (3042, 3004, 'OBJECTIVE', '업무 수행을 위해 추가적인 지원이 필요하다고 느끼시나요?', NULL, 'REQUIRED', NOW(), NOW(), 1, 1);
/*객관식 선택지*/
INSERT INTO objective_options (
    option_id, question_id, option_content, option_score
) VALUES
      (4001, 3040, '매우 여유롭다', 1),
      (4002, 3040, '적당하다', 3),
      (4003, 3040, '다소 많다', 4),
      (4004, 3040, '과도하다', 5);
INSERT INTO objective_options (
    option_id, question_id, option_content, option_score
) VALUES
      (4010, 3041, '전혀 몰입되지 않는다', 1),
      (4011, 3041, '보통이다', 3),
      (4012, 3041, '대체로 몰입하고 있다', 4),
      (4013, 3041, '매우 몰입하고 있다', 5);
INSERT INTO objective_options (
    option_id, question_id, option_content, option_score
) VALUES
      (4020, 3042, '전혀 필요하지 않다', 1),
      (4021, 3042, '크게 필요하지 않다', 2),
      (4022, 3042, '어느 정도 필요하다', 4),
      (4023, 3042, '즉각적인 지원이 필요하다', 5);


INSERT INTO evaluation_question (
    question_id, section_id,
    question_type, question_content,
    rating_scale, required_status,
    created_at, updated_at, created_by, updated_by
) VALUES
      (3030, 3004, 'SUBJECTIVE', '이번 평가 기간 동안 가장 잘했다고 생각하는 점은 무엇인가요?', NULL, 'REQUIRED', NOW(), NOW(), 1, 1),
      (3031, 3004, 'SUBJECTIVE', '개선이 필요하다고 느낀 부분은 무엇인가요?', NULL, 'REQUIRED', NOW(), NOW(), 1, 1);
