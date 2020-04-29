CREATE TABLE client (
    id BIGINT NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role INTEGER NOT NULL CHECK(role BETWEEN 0 AND 2),
    balance NUMERIC(19, 2) NOT NULL CHECK (balance >= 0)
);