CREATE TABLE books
(
    id              SERIAL PRIMARY KEY,
    isbn            VARCHAR(20) UNIQUE NOT NULL,
    page_count      INT                NOT NULL,
    cover_image_url TEXT,
    price           DECIMAL(10, 2)     NOT NULL,
    author_id       INT REFERENCES authors (id)
);

CREATE TABLE book_translations
(
    id          SERIAL PRIMARY KEY,
    book_id     INT          NOT NULL REFERENCES books (id),
    title       VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE book_genres
(
    id      SMALLSERIAL PRIMARY KEY,
    book_id INT         NOT NULL REFERENCES books (id) ON DELETE CASCADE,
    genre   VARCHAR(50) NOT NULL
);

CREATE TABLE book_authors
(
    book_id   INT NOT NULL REFERENCES books (id),
    author_id INT NOT NULL REFERENCES authors (id),
    PRIMARY KEY (book_id, author_id)
);

