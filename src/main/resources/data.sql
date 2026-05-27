-- =========================
-- PUBLISHERS
-- =========================

INSERT INTO publishers (id, name) VALUES
(1, 'Agora'),
(2, 'Wydawnictwo Literackie'),
(3, 'Albatros'),
(4, 'Czarna Owca'),
(5, 'Prószyński i S-ka');

-- =========================
-- AUTHORS
-- =========================

INSERT INTO authors (id, first_name, last_name, description, birth_date, death_date) VALUES
(1, 'Maciej', 'Siembieda', 'Polski autor thrillerów i powieści sensacyjnych.', '1961-01-01', NULL),
(2, 'Robert', 'Małecki', 'Autor bestsellerowych kryminałów.', '1977-01-01', NULL),
(3, 'Jo', 'Nesbo', 'Norweski autor kryminałów o Harrym Hole.', '1960-03-29', NULL),
(4, 'Stephen', 'King', 'Mistrz horroru i thrillera.', '1947-09-21', NULL),
(5, 'Agatha', 'Christie', 'Królowa kryminału.', '1890-09-15', '1976-01-12'),
(6, 'Remigiusz', 'Mróz', 'Polski autor thrillerów prawniczych i kryminałów.', '1987-01-15', NULL),
(7, 'Harlan', 'Coben', 'Autor thrillerów i powieści sensacyjnych.', '1962-01-04', NULL),
(8, 'Stieg', 'Larsson', 'Szwedzki dziennikarz i autor Millennium.', '1954-08-15', '2004-11-09');

-- =========================
-- BOOKS
-- =========================

INSERT INTO books (
    id,
    polish_title,
    original_title,
    description,
    release_date,
    isbn,
    publisher_id,
    cover_url
) VALUES
(1, 'Katharsis', 'Katharsis', 'Thriller historyczno-sensacyjny.', '2022-05-18', '9788326839991', 1, NULL),

(2, 'Wada', 'Wada', 'Kryminał z Bernardem Grossem.', '2020-09-02', '9788381693767', 3, NULL),

(3, 'Pierwszy śnieg', 'Snomannen', 'Kryminał z Harrym Hole.', '2007-01-01', '9788381250120', 4, NULL),

(4, 'Lśnienie', 'The Shining', 'Klasyczny horror psychologiczny.', '1977-01-28', '9788379856327', 5, NULL),

(5, 'Morderstwo w Orient Expressie', 'Murder on the Orient Express', 'Klasyczny kryminał Poirot.', '1934-01-01', '9780007119316', 2, NULL),

(6, 'Kasacja', 'Kasacja', 'Thriller prawniczy.', '2015-02-18', '9788380150476', 3, NULL),

(7, 'Nie mów nikomu', 'Tell No One', 'Thriller o zaginięciu i tajemnicach.', '2001-06-19', '9780307345897', 3, NULL),

(8, 'Dziewczyna z tatuażem', 'The Girl with the Dragon Tattoo', 'Pierwsza część Millennium.', '2005-08-01', '9788308061174', 4, NULL);

-- =========================
-- BOOKS_AUTHORS
-- =========================

INSERT INTO books_authors (books_id, authors_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8);

-- =========================
-- BOOK COPIES
-- =========================

INSERT INTO book_copies (id, book_id, status) VALUES
(1, 1, 'AVAILABLE'),
(2, 1, 'BORROWED'),
(3, 2, 'AVAILABLE'),
(4, 2, 'AVAILABLE'),
(5, 3, 'BORROWED'),
(6, 4, 'AVAILABLE'),
(7, 5, 'DISCONTINUED'),
(8, 6, 'AVAILABLE'),
(9, 7, 'BORROWED'),
(10, 8, 'AVAILABLE');

-- =========================
-- USERS
-- =========================

INSERT INTO users (
    id,
    email,
    last_name,
    first_name,
    library_card_number,
    birth_date,
    role,
    phone_number
) VALUES
(1, 'jan.kowalski@example.com', 'Kowalski', 'Jan', 'LIB001', '1990-04-12', 'READER', '500100200'),

(2, 'anna.nowak@example.com', 'Nowak', 'Anna', 'LIB002', '1985-09-01', 'READER', '501200300'),

(3, 'librarian@example.com', 'Wisniewska', 'Maria', 'EMP001', '1980-02-20', 'LIBRARIAN', '600700800'),

(4, 'admin@example.com', 'Zielinski', 'Tomasz', 'ADM001', '1975-11-15', 'ADMIN', '700800900');

-- =========================
-- LOANS
-- =========================

INSERT INTO loans (
    id,
    book_copy_id,
    user_id,
    loan_date,
    due_date,
    return_date,
    status
) VALUES
(1, 2, 1, '2026-05-01', '2026-05-15', NULL, 'ACTIVE'),

(2, 5, 2, '2026-04-01', '2026-04-15', NULL, 'OVERDUE'),

(3, 9, 1, '2026-03-01', '2026-03-15', '2026-03-10', 'RETURNED');

-- =========================
-- RESERVATIONS
-- =========================

INSERT INTO reservations (
    id,
    book_id,
    user_id,
    reservation_date,
    expiration_date,
    status
) VALUES
(1, 3, 1, '2026-05-20', '2026-05-27', 'PENDING'),

(2, 1, 2, '2026-05-10', '2026-05-17', 'FULFILLED'),

(3, 4, 1, '2026-04-01', '2026-04-08', 'CANCELLED');