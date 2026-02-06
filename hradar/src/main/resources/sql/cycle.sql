INSERT INTO cycle (
    cycle_id, company_id, cycle_name,
    year, quarter,
    start_date, end_date,
    status, emp_id,
    is_deleted, is_comp_report_generated,
    created_at, updated_at, created_by, updated_by
) VALUES
    (1, 1, '2026년 1분기 다면평가',
     '2026', 'Q1',
     '2026-01-01 00:00:00', '2026-03-31 23:59:59',
     'DRAFT', 1,
     'N', 'N',
     '2026-01-01 09:00:00', '2026-03-31 18:00:00', 1, 1);
