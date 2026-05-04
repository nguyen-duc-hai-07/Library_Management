CREATE TABLE authors (
                         id          SERIAL PRIMARY KEY,
                         name        VARCHAR(255) NOT NULL,
                         birth_year  INT,
                         description TEXT,
                         is_deleted  BOOLEAN DEFAULT FALSE
);