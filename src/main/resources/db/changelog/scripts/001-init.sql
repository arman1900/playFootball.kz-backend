create table player
(
    id                  bigint  not null primary key,
    created_on          timestamp,
    entity_version      bigint,
    updated_on          timestamp,
    password            varchar(255),
    first_name          varchar(255),
    last_name           varchar(255),
    rating              double precision,
    phone_number        varchar(255)
        constraint ukqyvo60cstlkecvqxjo25dnoox
            unique
);

create sequence hibernate_sequence;
