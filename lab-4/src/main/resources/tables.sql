CREATE TABLE books(
                       id SERIAL PRIMARY KEY,
                       isbn VARCHAR NOT NULL UNIQUE,
                       title VARCHAR NOT NULL,
                       author VARCHAR NOT NULL,
                       publication_year INTEGER NOT NULL
);