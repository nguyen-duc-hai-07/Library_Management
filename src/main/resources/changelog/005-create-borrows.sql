CREATE TYPE borrow_status AS ENUM ('BORROWING', 'RETURNED');
CREATE TABLE borrows (
                         id          SERIAL PRIMARY KEY,
                         user_id     INT NOT NULL,
                         book_id     INT NOT NULL,
                         borrow_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         return_date TIMESTAMP,
                         due_date    TIMESTAMP NOT NULL,
                         status      borrow_status,
                         is_deleted  BOOLEAN DEFAULT FALSE,
                         FOREIGN KEY (user_id) REFERENCES users(id),
                         FOREIGN KEY (book_id) REFERENCES books(id)
);


