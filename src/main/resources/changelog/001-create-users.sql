CREATE TYPE user_role AS ENUM ('READER', 'ADMIN');
CREATE TYPE user_status AS ENUM ('ACTIVE', 'LOCKED');
CREATE TABLE users (
                       id            SERIAL PRIMARY KEY,
                       full_name     VARCHAR(255) NOT NULL,
                       email         VARCHAR(255) UNIQUE NOT NULL,
                       phone_number  VARCHAR(20) NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       role          user_role,
                       status        user_status,
                       created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       is_deleted    BOOLEAN DEFAULT FALSE
);