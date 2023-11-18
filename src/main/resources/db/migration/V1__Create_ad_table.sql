CREATE TABLE ad
(
    id      uuid NOT NULL,
    subject text NOT NULL,
    body    text NOT NULL,
    price   int, -- TODO: make this a money type?
    email   text NOT NULL
);
