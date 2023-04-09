CREATE TABLE customer
(
    id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    middle_name TEXT,
    last_name TEXT NOT NULL,
    suffix TEXT,
    email TEXT,
    phone
);

ALTER SEQUENCE customer_id_sec RESTART 1000000;