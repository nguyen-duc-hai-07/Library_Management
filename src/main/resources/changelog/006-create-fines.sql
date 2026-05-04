CREATE TYPE fine_status AS ENUM ('UNPAID', 'PAID');
CREATE TABLE fines (
                       id          SERIAL PRIMARY KEY,
                       borrow_id   INT NOT NULL,
                       user_id     INT NOT NULL,
                       days_late   INT NOT NULL,
                       fine_amount DECIMAL(10,2) NOT NULL,
                       status      fine_status,
                       paid_at     TIMESTAMP,
                       is_deleted  BOOLEAN DEFAULT FALSE,
                       FOREIGN KEY (borrow_id) REFERENCES borrows(id),
                       FOREIGN KEY (user_id) REFERENCES users(id)
);