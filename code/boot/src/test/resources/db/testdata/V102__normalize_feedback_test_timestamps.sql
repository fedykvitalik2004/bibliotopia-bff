UPDATE bug_reports
SET created_at =
        TIMESTAMPTZ '2026-03-25 15:11:00+00'
            + (substring(title FROM '^Bug #([1-5])$')::integer * INTERVAL '24 hours')
WHERE title ~ '^Bug #[1-5]$';

UPDATE feature_requests
SET created_at =
        TIMESTAMPTZ '2026-03-26 11:11:00+00'
            + (substring(title FROM '^Feature #([1-3])$')::integer * INTERVAL '24 hours')
WHERE title ~ '^Feature #[1-3]$';
