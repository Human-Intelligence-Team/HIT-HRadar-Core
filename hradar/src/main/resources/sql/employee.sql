INSERT INTO employee (
    emp_id, created_at, updated_at, birth, com_id, dept_id, email,
    employee_no, type, exit_date, extension_number, gender,
    hire_date, image, is_deleted, name, note, phone_number, position_id
) VALUES
      (1001, NOW(6), NULL, '19950101', 1, 1, 'user1001@company.com', 'EMP1001', 'WORKING', NULL, '1001', 'MALE', '2020-01-02', NULL, 'N', '김사원1', NULL, '010-1000-1001', 1),
      (1002, NOW(6), NULL, '19950202', 1, 2, 'user1002@company.com', 'EMP1002', 'WORKING', NULL, '1002', 'FEMALE', '2020-01-03', NULL, 'N', '김사원2', NULL, '010-1000-1002', 2),
      (1003, NOW(6), NULL, '19950303', 1, 3, 'user1003@company.com', 'EMP1003', 'WORKING', NULL, '1003', 'MALE', '2020-01-04', NULL, 'N', '김사원3', NULL, '010-1000-1003', 3),
      (1004, NOW(6), NULL, '19950404', 1, 1, 'user1004@company.com', 'EMP1004', 'WORKING', NULL, '1004', 'FEMALE', '2020-01-05', NULL, 'N', '김사원4', NULL, '010-1000-1004', 1),
      (1005, NOW(6), NULL, '19950505', 1, 2, 'user1005@company.com', 'EMP1005', 'WORKING', NULL, '1005', 'MALE', '2020-01-06', NULL, 'N', '김사원5', NULL, '010-1000-1005', 2),
      (1006, NOW(6), NULL, '19950606', 1, 3, 'user1006@company.com', 'EMP1006', 'WORKING', NULL, '1006', 'FEMALE', '2020-01-07', NULL, 'N', '김사원6', NULL, '010-1000-1006', 3),
      (1007, NOW(6), NULL, '19950707', 1, 1, 'user1007@company.com', 'EMP1007', 'WORKING', NULL, '1007', 'MALE', '2020-01-08', NULL, 'N', '김사원7', NULL, '010-1000-1007', 1),
      (1008, NOW(6), NULL, '19950808', 1, 2, 'user1008@company.com', 'EMP1008', 'WORKING', NULL, '1008', 'FEMALE', '2020-01-09', NULL, 'N', '김사원8', NULL, '010-1000-1008', 2),
      (1009, NOW(6), NULL, '19950909', 1, 3, 'user1009@company.com', 'EMP1009', 'WORKING', NULL, '1009', 'MALE', '2020-01-10', NULL, 'N', '김사원9', NULL, '010-1000-1009', 3),
      (1010, NOW(6), NULL, '19951010', 1, 1, 'user1010@company.com', 'EMP1010', 'WORKING', NULL, '1010', 'FEMALE', '2020-01-11', NULL, 'N', '김사원10', NULL, '010-1000-1010', 1)


INSERT INTO department (
    dept_id,
    created_at,
    updated_at,
    com_id,
    dept_name,
    dept_phone,
    is_deleted,
    manager_employee_id,
    parent_dept_id
) VALUES
      (1, NOW(6), NULL, 1, '개발팀', '02-100-0001', 'N', 1001, NULL),
      (2, NOW(6), NULL, 1, '인사팀', '02-100-0002', 'N', 1002, NULL),
      (3, NOW(6), NULL, 1, '영업팀', '02-100-0003', 'N', 1003, NULL);
