CREATE TABLE orders
(
    id               SERIAL PRIMARY KEY,
    status           VARCHAR(50) NOT NULL,
    last_status_date TIMESTAMPTZ,
    total_price      DECIMAL(10, 2),
    user_id          INT         NOT NULL REFERENCES users (id)
);

CREATE TABLE order_items
(
    id       SERIAL PRIMARY KEY,
    order_id INT            NOT NULL REFERENCES orders (id),
    book_id  INT            NOT NULL REFERENCES books (id),
    price    DECIMAL(10, 2) NOT NULL,
    amount   INT            NOT NULL CHECK (amount > 0)
);