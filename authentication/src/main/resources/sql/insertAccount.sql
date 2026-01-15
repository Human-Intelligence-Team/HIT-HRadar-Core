INSERT INTO account (
    company_code,
    employee_id,
    login_id,
    email,
    password,
    name,
    role,
    status
) VALUES
      ('COMP001', 1001, 'user01', 'user01@test.com', '$2a$10$hashpassword01', '김민수', 'user', 'ACTIVE'),
      ('COMP002', 1002, 'user02', 'user02@test.com', '$2a$10$hashpassword02', '이서연', 'user', 'ACTIVE'),
      ('COMP003', 1003, 'user03', 'user03@test.com', '$2a$10$hashpassword03', '박지훈', 'user', 'ACTIVE'),
      ('COMP004', 1004, 'user04', 'user04@test.com', '$2a$10$hashpassword04', '최유진', 'user', 'ACTIVE'),
      ('COMP005', 1005, 'user05', 'user05@test.com', '$2a$10$hashpassword05', '정우성', 'user', 'RETIRED'),
      ('COMP006', 2001, 'admin01', 'admin01@test.com', '$2a$10$hashpassword06', '관리자1', 'admin', 'ACTIVE'),
      ('COMP007', 2002, 'admin02', 'admin02@test.com', '$2a$10$hashpassword07', '관리자2', 'admin', 'ACTIVE'),
      ('COMP008', NULL, 'user06', 'user06@test.com', '$2a$10$hashpassword08', '한지민', 'user', 'ACTIVE'),
      ('COMP009', NULL, 'user07', 'user07@test.com', '$2a$10$hashpassword09', '오세훈', 'user', 'ACTIVE'),
      ('COMP010', NULL, 'user08', 'user08@test.com', '$2a$10$hashpassword10', '윤하늘', 'user', 'ACTIVE');
