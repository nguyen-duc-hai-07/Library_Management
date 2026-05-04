CREATE TABLE categories (
                            id         SERIAL PRIMARY KEY,
                            name       VARCHAR(255) NOT NULL,
                            is_deleted BOOLEAN DEFAULT FALSE
);