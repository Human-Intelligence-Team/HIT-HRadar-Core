INSERT INTO kpi_progress_log (
    kpi_log_id, kpi_id,
    log_date, log_owner_id,
    log_value,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
      (1, 1001, '2026-03-31', 1007, 280, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1),

      (2, 1002, '2026-03-31', 1008, 210, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1),

      (3, 1003, '2026-03-31', 1009, 270, 'N',
       '2026-03-31 18:00:00', '2026-03-31 18:00:00', 1, 1);
