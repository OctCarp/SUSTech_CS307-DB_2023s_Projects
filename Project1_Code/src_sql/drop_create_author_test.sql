DROP TABLE IF EXISTS authors;

CREATE TABLE IF NOT EXISTS authors
(
    a_id integer PRIMARY KEY ,
    author_name varchar NOT NULL UNIQUE ,
    author_registration_time timestamp NOT NULL UNIQUE,
    author_id char(18) NOT NULL UNIQUE
    check ( length(author_id) = 18),
    author_phone_number char(11) NOT NULL UNIQUE
    check ( length(author_phone_number) = 11)

);