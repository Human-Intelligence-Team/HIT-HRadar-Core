INSERT INTO okr_key_result (
    key_result_id, goal_id,
    key_result_content,
    okr_metric_name,
    key_target_value,
    is_achieved,
    is_deleted,
    created_at, updated_at, created_by, updated_by
) VALUES
      (3001, 202, '월간 장애 건수 감소', '장애 건수', 100, 'N', 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1),

      (3002, 206, '배포 실패율 감소', '배포 안정성', 100, 'N', 'N',
       '2026-01-01 09:00:00', '2026-01-01 09:00:00', 1, 1);
