-- =====================회차==================================
-- 진행중
INSERT INTO cycle (
    cycle_id, company_id, cycle_name,
    year, quarter,
    start_date, end_date,
    status, emp_id,
    is_deleted, is_comp_report_generated,
    created_at, updated_at, created_by, updated_by
) VALUES
    (100, 4, '2026년 1분기 다면평가',
     '2026', 'Q1',
     '2026-01-01 00:00:00', '2026-03-31 23:59:59',
     'IN_PROGRESS', 1001,
     'N', 'N',
     '2026-01-01 09:00:00', '2026-03-31 18:00:00', 1001, 1001);

-- 초안
INSERT INTO cycle (
    cycle_id, company_id, cycle_name,
    year, quarter,
    start_date, end_date,
    status, emp_id,
    is_deleted, is_comp_report_generated,
    created_at, updated_at, created_by, updated_by
) VALUES
    (101, 4, '2026년 2분기 다면평가',
     '2026', 'Q2',
     '2026-03-31 00:00:00', '2026-05-31 23:59:59',
     'DRAFT', 1001,
     'N', 'N',
     '2026-01-01 09:00:00', '2026-03-31 18:00:00', 1001, 1001);

-- =====================평가유형==================================
INSERT INTO evaluation_type (
    eval_type_id, type_name, company_id,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
      (1, '동료평가', 4, 'N', '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (2, '상향평가', 4, 'N', '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (3, '하향평가', 4, 'N', '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (4, '자기평가', 4, 'N', NOW(), NOW(), 1, 1);

-- =====================평가유형과 회차 매핑==================================
INSERT INTO cycle_evaluation_type (
    cycle_eval_type_id, cycle_id, eval_type_id,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
      (1001, 100, 1, 'N', '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (1002, 100, 2, 'N', '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (1003, 100, 3, 'N', '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (1004, 100, 4, 'N', NOW(), NOW(), 1, 1);

-- =====================섹션 생성==================================
INSERT INTO evaluation_section (
    section_id, cycle_eval_type_id,
    section_title, section_description, section_order,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
      (2001, 1001, '협업', '팀 내 협업 역량 평가', 1, 'N','2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (2002, 1002, '협업', '팀 내 협업 역량 평가', 1, 'N','2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (2003, 1003, '협업', '팀 내 협업 역량 평가', 1, 'N','2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (3001, 1004, '직무만족도', '본인의 직무 만족도를 평가합니다', 1, 'N', NOW(), NOW(), 1, 1),
      (3002, 1004, '자기성장', '직무를 통한 개인 성장 수준을 평가합니다', 2, 'N', NOW(), NOW(), 1, 1),
      (3003, 1004, '업무주도성', '업무에 대한 주도적 태도를 평가합니다', 3, 'N', NOW(), NOW(), 1, 1),
      (3004, 1004, '자기회고', '본인의 업무를 돌아봅니다', 4, 'N', NOW(), NOW(), 1, 1);

-- =====================문항 생성==================================
INSERT INTO evaluation_question (
    question_id, section_id,
    question_type, question_content,
    rating_scale, required_status,
    created_at, updated_at, created_by, updated_by
) VALUES
      (3001, 2001, 'RATING', '팀원과 원활하게 소통한다', 5, 'REQUIRED','2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (3002, 2001, 'RATING', '갈등 상황에서도 협력적으로 행동한다', 5, 'REQUIRED','2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (3003, 2001, 'RATING', '공동 목표 달성에 기여한다', 5, 'REQUIRED','2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
      (3004, 2001, 'RATING', '타인의 의견을 존중한다', 5, 'REQUIRED','2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),
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


-- =========================평가 할당 ==========================
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
 '2026-03-01 09:00:00', '2026-03-30 12:00:00', 1, 1),

-- 자기평가 (1007 → 1007)
(1114, 1004, 1007, 1007, 'SUBMITTED', NOW(), 'N', NOW(), NOW(), 1, 1);

-- =========================평가 응답========================
INSERT INTO evaluation_response (
    response_id, assignment_id, question_id,
    response_type, score,
    created_at, updated_at, created_by, updated_by
) VALUES
-- 동료평가 (assignment_id = 1111)
(6001, 1111, 3001, 'RATING', 4, '2026-03-30 10:01:00', '2026-03-30 10:01:00', 1, 1),
(6002, 1111, 3002, 'RATING', 5, '2026-03-30 10:01:00', '2026-03-30 10:01:00', 1, 1),
(6003, 1111, 3003, 'RATING', 4, '2026-03-30 10:01:00', '2026-03-30 10:01:00', 1, 1),
(6004, 1111, 3004, 'RATING', 5, '2026-03-30 10:01:00', '2026-03-30 10:01:00', 1, 1),

-- 하향평가 (assignment_id = 1112)
(6005, 1112, 3001, 'RATING', 3, '2026-03-30 11:01:00', '2026-03-30 11:01:00', 1, 1),
(6006, 1112, 3002, 'RATING', 4, '2026-03-30 11:01:00', '2026-03-30 11:01:00', 1, 1),
(6007, 1112, 3003, 'RATING', 3, '2026-03-30 11:01:00', '2026-03-30 11:01:00', 1, 1),
(6008, 1112, 3004, 'RATING', 4, '2026-03-30 11:01:00', '2026-03-30 11:01:00', 1, 1),

-- 상향평가 (assignment_id = 1113)
(6009, 1113, 3001, 'RATING', 5, '2026-03-30 12:01:00', '2026-03-30 12:01:00', 1, 1),
(6010, 1113, 3002, 'RATING', 5, '2026-03-30 12:01:00', '2026-03-30 12:01:00', 1, 1),
(6011, 1113, 3003, 'RATING', 4, '2026-03-30 12:01:00', '2026-03-30 12:01:00', 1, 1),
(6012, 1113, 3004, 'RATING', 5, '2026-03-30 12:01:00', '2026-03-30 12:01:00', 1, 1);
INSERT INTO evaluation_response (
    response_id,
    assignment_id,
    question_id,
    response_type,
    score,
    text_answer,
    option_id,
    created_at,
    updated_at,
    created_by,
    updated_by
) VALUES
      (6013, 1114, 3005, 'RATING', 4, NULL, NULL, NOW(), NOW(), 1, 1),
      (6014, 1114, 3006, 'RATING', 3, NULL, NULL, NOW(), NOW(), 1, 1),
      (6015, 1114, 3007, 'RATING', 5, NULL, NULL, NOW(), NOW(), 1, 1),
      (6016, 1114, 3008, 'RATING', 4, NULL, NULL, NOW(), NOW(), 1, 1);
