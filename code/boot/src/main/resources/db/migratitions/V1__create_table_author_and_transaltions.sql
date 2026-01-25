CREATE TABLE author (
    id UUID PRIMARY KEY,
    birth_date DATE,
    death_date DATE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
    version BIGINT DEFAULT 0
);