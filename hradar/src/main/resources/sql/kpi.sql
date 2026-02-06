INSERT INTO kpi_detail (
    kpi_id, goal_id,
    kpi_metric_name,
    kpi_start_value, kpi_target_value,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
      (1001, 201, '신규 고객 매출', 0, 400, 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      (1002, 204, '기존 고객 유지 매출', 0, 300, 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      (1003, 203, '파트너 매출', 0, 300, 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);

