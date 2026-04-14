create table authors
(
    id         bigserial primary key,
    birth_date date         not null,
    death_date date,
    constraint check_death_date_after_birth
        check (death_date is null or death_date >= birth_date)
);

create table author_translations
(
    id          bigserial primary key,
    first_name  varchar(100) not null,
    last_name   varchar(100) not null,
    description text         not null,
    language    varchar(5)   not null,
    author_id   bigint          not null references authors (id) on delete cascade
);