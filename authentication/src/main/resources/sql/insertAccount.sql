INSERT INTO hit.user_account (
    com_id,
    company_code,
    employee_id,
    login_id,
    email,
    password,
    name,
    role,
    status
) VALUES
      (1, 'COMP001', 1001, 'user01', 'user01@test.com', '$2a$10$CgyohMOiILQ7Y612C5vvn.JLDb085wxSJA0aSnrdWhEtoV2KrCR3y', '김민수', 'user', 'ACTIVE'),
      (2,'COMP002', 1002, 'user02', 'user02@test.com', '$2a$10$CgyohMOiILQ7Y612C5vvn.JLDb085wxSJA0aSnrdWhEtoV2KrCR3y', '이서연', 'user', 'ACTIVE'),
      (2,'COMP003', 1003, 'user03', 'user03@test.com', '$2a$10$CgyohMOiILQ7Y612C5vvn.JLDb085wxSJA0aSnrdWhEtoV2KrCR3y', '박지훈', 'user', 'ACTIVE'),
      (1,'COMP004', 1004, 'user04', 'user04@test.com', '$2a$10$CgyohMOiILQ7Y612C5vvn.JLDb085wxSJA0aSnrdWhEtoV2KrCR3y', '최유진', 'user', 'ACTIVE'),
      (1,'COMP005', 1005, 'user05', 'user05@test.com', '$2a$10$CgyohMOiILQ7Y612C5vvn.JLDb085wxSJA0aSnrdWhEtoV2KrCR3y', '정우성', 'user', 'INACTIVE'),
      (1,'COMP006', 2001, 'admin01', 'admin01@test.com', '$2a$10$CgyohMOiILQ7Y612C5vvn.JLDb085wxSJA0aSnrdWhEtoV2KrCR3y', '관리자1', 'admin', 'ACTIVE'),
      (2,'COMP007', 2002, 'admin02', 'admin02@test.com', '$2a$10$CgyohMOiILQ7Y612C5vvn.JLDb085wxSJA0aSnrdWhEtoV2KrCR3y', '관리자2', 'admin', 'ACTIVE'),
      (1,'COMP008', NULL, 'user06', 'user06@test.com', '$2a$10$CgyohMOiILQ7Y612C5vvn.JLDb085wxSJA0aSnrdWhEtoV2KrCR3y', '한지민', 'user', 'ACTIVE'),
      (2,'COMP009', NULL, 'user07', 'user07@test.com', '$2a$10$CgyohMOiILQ7Y612C5vvn.JLDb085wxSJA0aSnrdWhEtoV2KrCR3y', '오세훈', 'user', 'ACTIVE'),
      (1,'COMP010', NULL, 'user08', 'user08@test.com', '$2a$10$CgyohMOiILQ7Y612C5vvn.JLDb085wxSJA0aSnrdWhEtoV2KrCR3y', '윤하늘', 'user', 'ACTIVE');
