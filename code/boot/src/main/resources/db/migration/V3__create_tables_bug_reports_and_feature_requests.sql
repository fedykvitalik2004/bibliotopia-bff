CREATE TABLE bug_reports (
    id SERIAL PRIMARY KEY,
    title VARCHAR(140) NOT NULL,
    description TEXT,
    importance INTEGER NOT NULL,
    stack_trace TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE feature_requests (
    id SERIAL PRIMARY KEY,
    title VARCHAR(140) NOT NULL,
    description TEXT,
    importance INTEGER NOT NULL,
    votes_count INTEGER,
    created_at TIMESTAMPTZ DEFAULT NOW()
);