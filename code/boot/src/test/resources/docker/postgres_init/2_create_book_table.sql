CREATE TABLE books (
    id UUID PRIMARY KEY,
    isbn VARCHAR(50),
    title VARCHAR(255) NOT NULL,
    page_count INT,
    cover_image_url TEXT,
    book_genre INT,
    created_at TIMESTAMP WITH TIME ZONE,
    version BIGINT DEFAULT 0
);

CREATE TABLE book_price_history (
    id UUID PRIMARY KEY,
    price NUMERIC(6, 2) NOT NULL,
    change_date TIMESTAMP WITH TIME ZONE NOT NULL,
    book_id UUID NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE,
    version BIGINT DEFAULT 0,
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

ALTER TABLE book_author
ADD CONSTRAINT FK_book_author_book
FOREIGN KEY (book_id)
REFERENCES books(id)
ON DELETE CASCADE;