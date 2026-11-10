INSERT INTO feature_requests (title, description, importance, votes_count, created_at)
SELECT
    'Feature #' || g,
    'Auto generated description ' || g,
    CASE g WHEN 3 THEN 2 ELSE 7 END,
    0,
    '2026-03-26 11:11:00+00'::timestamptz + (g * INTERVAL '1 day')
FROM generate_series(1, 3) AS g;
