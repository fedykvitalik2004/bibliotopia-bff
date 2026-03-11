CREATE TABLE authors
(
    id         SERIAL PRIMARY KEY,
    birth_date DATE         NOT NULL,
    death_date DATE,
    CONSTRAINT check_death_date_after_birth
        CHECK (death_date IS NULL OR death_date >= birth_date)
);

CREATE TABLE author_translations
(
    id          SERIAL PRIMARY KEY,
    first_name  VARCHAR(100) NOT NULL,
    last_name   VARCHAR(100) NOT NULL,
    description TEXT         NOT NULL,
    author_id   INT          NOT NULL REFERENCES authors (id) ON DELETE CASCADE
);