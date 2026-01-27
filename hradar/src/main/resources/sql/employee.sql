INSERT INTO employee (
    emp_id,
    com_id,
    dept_id,
    position_id,
    employee_no,
    name,
    email,
    gender,
    birth,
    hire_date,
    exit_date,
    image,
    cellphone_no,
    telephone_no,
    note,
    type,
    is_deleted,
    created_at,
    updated_at
) VALUES
      (1001, 1, 10, 1, 'EMP-1001', '김민수', 'minsu.kim@company.com', 'MALE',   '19900115', '2018-03-01', NULL, NULL, '01012341234', NULL, '백엔드 개발자', 'WORKING', 'N', NOW(), NOW()),
      (1002, 1, 10, 2, 'EMP-1002', '이서연', 'seoyeon.lee@company.com', 'FEMALE', '19920322', '2019-06-10', NULL, NULL, '01023452345', NULL, '프론트엔드 개발자', 'WORKING', 'N', NOW(), NOW()),
      (1003, 1, 10, 3, 'EMP-1003', '박준호', 'junho.park@company.com', 'MALE',   '19881203', '2017-01-15', NULL, NULL, '01034563456', NULL, '팀장', 'WORKING', 'N', NOW(), NOW()),
      (1004, 1, 10, 4, 'EMP-1004', '최유진', 'yujin.choi@company.com', 'FEMALE', '19950708', '2020-09-01', NULL, NULL, '01045674567', NULL, 'HR 담당', 'WORKING', 'N', NOW(), NOW()),
      (1005, 1, 20, 4, 'EMP-1005', '정우성', 'woosung.jung@company.com', 'MALE',   '19850511', '2015-11-20', NULL, NULL, '01056785678', NULL, 'HR 팀장', 'WORKING', 'N', NOW(), NOW()),

      (1006, 1, 10, 5, 'EMP-1006', '한지민', 'jimin.han@company.com', 'FEMALE', '19941130', '2021-02-01', NULL, NULL, '01067896789', NULL, '마케팅 매니저', 'WORKING', 'N', NOW(), NOW()),
      (1007, 1, 30, 6, 'EMP-1007', '오세훈', 'sehun.oh@company.com', 'MALE',   '19900219', '2019-08-12', NULL, NULL, '01078907890', NULL, '마케팅 사원', 'WORKING', 'N', NOW(), NOW()),
      (1008, 1, 40, 7, 'EMP-1008', '윤아름', 'areum.yoon@company.com', 'FEMALE', '19960914', '2022-01-03', NULL, NULL, '01089018901', NULL, '디자이너', 'WORKING', 'N', NOW(), NOW()),
      (1009, 1, 40, 7, 'EMP-1009', '장동건', 'donggun.jang@company.com', 'MALE',   '19871225', '2016-04-18', NULL, NULL, '01090129012', NULL, 'UX 리드', 'WORKING', 'N', NOW(), NOW()),

      (1010, 1, 50, 8, 'EMP-1010', '서지혜', 'jihye.seo@company.com', 'FEMALE', '19930309', '2023-07-01', NULL, NULL, '01001230123', NULL, 'QA 엔지니어', 'WORKING', 'N', NOW(), NOW()),
      (1011, 1, 50, 8, 'EMP-1011', '임재현', 'jaehyun.lim@company.com', 'MALE',   '19911001', '2014-02-10', NULL, NULL, '01012340123', NULL, 'QA 팀장', 'WORKING', 'N', NOW(), NOW()),

-- 휴직
      (1012, 1, 10, 1, 'EMP-1012', '문수빈', 'subin.moon@company.com', 'FEMALE', '19951217', '2020-05-01', NULL, NULL, '01023450123', NULL, '육아 휴직', 'LEAVE', 'N', NOW(), NOW()),

-- 퇴사
      (1013, 1, 20, 4, 'EMP-1013', '강동원', 'dongwon.kang@company.com', 'MALE',   '19840612', '2012-03-01', '2024-12-31', NULL, '01034560123', NULL, '퇴사자', 'RESIGNED', 'N', NOW(), NOW()),

      (1014, 1, 30, 6, 'EMP-1014', '배수지', 'suzy.bae@company.com', 'FEMALE', '19941010', '2018-10-15', NULL, NULL, '01045670123', NULL, '콘텐츠 마케터', 'WORKING', 'N', NOW(), NOW()),
      (1015, 1, 40, 7, 'EMP-1015', '유재석', 'jaeseok.yoo@company.com', 'MALE',   '19720814', '2008-01-01', NULL, NULL, '01056780123', NULL, '조직 문화 리더', 'WORKING', 'N', NOW(), NOW());
