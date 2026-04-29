---Publishers
insert into publishers (name) values ('Wydawnictwo Literackie');
insert into publishers (name) values ('Znak');
insert into publishers (name) values ('W.A.B.');

--Authors
insert into authors (last_name, first_name, description, birth_date, death_date)
values ('Sapkowski', 'Andrzej', 'Polski pisarz fantasy', '1948-06-21', null);
insert into authors (last_name, first_name, description, birth_date, death_date)
values ('Tolkien', 'John Ronald Reuel', 'Angielski pisarz i filolog', '1892-01-03', '1973-09-02');
insert into authors (last_name, first_name, description, birth_date, death_date)
values ('Tokarczuk', 'Olga', 'Polska pisarka, noblistka', '1962-01-29', null);

---Books
INSERT INTO books (polish_title, original_title, description, release_date, isbn, publisher_id, cover_url)
VALUES ('Wiedźmin', 'Wiedźmin', 'Zbiór opowiadań o Geralcie', '1990-01-01', '9788373192003', 1, NULL);
INSERT INTO books (polish_title, original_title, description, release_date, isbn, publisher_id, cover_url)
VALUES ('Władca Pierścieni', 'The Lord of the Rings', 'Epicka powieść fantasy', '1954-07-29', '9788324004201', 2, NULL);
INSERT INTO books (polish_title, original_title, description, release_date, isbn, publisher_id, cover_url)
VALUES ('Bieguni', 'Bieguni', 'Powieść Olgi Tokarczuk', '2007-01-01', '9788308043423', 3, NULL);

---books_authors
INSERT INTO books_authors (books_id, authors_id) VALUES (1, 1);
INSERT INTO books_authors (books_id, authors_id) VALUES (2, 2);
INSERT INTO books_authors (books_id, authors_id) VALUES (3, 3);