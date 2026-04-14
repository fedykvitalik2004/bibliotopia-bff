SELECT setseed(0.55);

INSERT INTO feature_requests (title, description, importance, votes_count, created_at)
SELECT
    'Feature #' || g,
    'Auto generated description ' || g,
    floor(random() * 10) + 1,
    floor(random() * 100),
    '2026-03-26 11:11:00+00'::timestamptz + (g * INTERVAL '1 day')
FROM generate_series(1, 3) AS g;