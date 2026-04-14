ALTER TABLE order_items
    ADD CONSTRAINT unique_order_book UNIQUE (order_id, book_id);