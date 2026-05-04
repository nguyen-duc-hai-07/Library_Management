

CREATE TABLE books (
                       id                 SERIAL PRIMARY KEY,
                       isbn               VARCHAR(255) UNIQUE NOT NULL,
                       title              VARCHAR(255) NOT NULL,
                       author_id          INT NOT NULL,
                       category_id        INT NOT NULL,
                       publisher          VARCHAR(255),
                       publish_year       VARCHAR(10),
                       description        TEXT,
                       total_quantity     INT DEFAULT 1,
                       available_quantity INT DEFAULT 1,
                       is_deleted         BOOLEAN DEFAULT FALSE,
                       FOREIGN KEY (author_id) REFERENCES authors(id),
                       FOREIGN KEY (category_id) REFERENCES categories(id)
);
