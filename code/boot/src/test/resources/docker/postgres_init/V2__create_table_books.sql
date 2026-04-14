create table books
(
    id              bigserial primary key,
    isbn            varchar(20) unique not null,
    page_count      int                not null,
    cover_image_url text,
    price           decimal(10, 2)     not null,
    author_id       bigint references authors (id)
);

create table book_translations
(
    id          bigserial primary key,
    book_id     bigint          not null references books (id),
    title       varchar(255) not null,
    description text,
    language varchar(5) not null
);

create table book_genres
(
    id      bigserial primary key,
    book_id bigint         not null references books (id) on delete cascade,
    genre   varchar(50) not null
);

create table book_authors
(
    book_id   bigint not null references books (id),
    author_id bigint not null references authors (id),
    primary key (book_id, author_id)
);

