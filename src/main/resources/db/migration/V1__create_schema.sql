CREATE TABLE users (
    id UUID PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    active BOOLEAN NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE books (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    author VARCHAR(255) NOT NULL,
    page_count INTEGER NOT NULL,
    publication_date DATE NOT NULL
);

CREATE TABLE book_copies (
    id UUID PRIMARY KEY,
    status VARCHAR(20) NOT NULL,
    book_id UUID NOT NULL,
    CONSTRAINT fk_book_copy_book
        FOREIGN KEY (book_id)
            REFERENCES books(id)
);

CREATE TABLE loans (
    id UUID PRIMARY KEY,
    loan_date TIMESTAMP NOT NULL,
    due_date TIMESTAMP NOT NULL,
    returned_at TIMESTAMP,
    status VARCHAR(20) NOT NULL,
    user_id UUID NOT NULL,
    book_copy_id UUID NOT NULL,
    CONSTRAINT fk_loan_user
        FOREIGN KEY (user_id)
            REFERENCES users(id),
    CONSTRAINT fk_loan_book_copy
        FOREIGN KEY (book_copy_id)
            REFERENCES book_copies(id)
);

CREATE TABLE fines (
    id UUID PRIMARY KEY,
    amount NUMERIC(10,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    paid_at TIMESTAMP,
    status VARCHAR(20) NOT NULL,
    loan_id UUID NOT NULL UNIQUE,
    CONSTRAINT fk_fine_loan
        FOREIGN KEY (loan_id)
            REFERENCES loans(id)
);

CREATE INDEX idx_books_isbn
    ON books(isbn);

CREATE INDEX idx_book_copies_book
    ON book_copies(book_id);

CREATE INDEX idx_loans_user
    ON loans(user_id);

CREATE INDEX idx_loans_book_copy
    ON loans(book_copy_id);

CREATE INDEX idx_fines_loan
    ON fines(loan_id);