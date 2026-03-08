CREATE TABLE author (
    id UUID PRIMARY KEY,
    birth_date DATE,
    death_date DATE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
    version BIGINT DEFAULT 0
);

CREATE TABLE author_translation (
    id UUID PRIMARY KEY,
    author_id UUID NOT NULL,
    language_code VARCHAR(10) NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
    biography TEXT,
    version BIGINT DEFAULT 0,
    CONSTRAINT fk_author_translation FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE
);

CREATE TABLE book_author (
    book_id UUID NOT NULL,
    author_id UUID NOT NULL,
    PRIMARY KEY (book_id, author_id),
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE
);