SELECT setseed(0.42);

INSERT INTO bug_reports (title, description, importance, stack_trace, created_at)
SELECT 'Bug #' || g, 'Description ' || g, (random() * 5)::int + 1, 'Trace at line ' || g, '2026-03-25 15:11:00+00'::timestamptz + (g * INTERVAL '1 day')
FROM generate_series(1, 5) AS g;