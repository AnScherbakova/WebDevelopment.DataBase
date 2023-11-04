CREATE TABLE car
(
    id    Integer primary key,
    brand VARCHAR NOT NULL,
    model VARCHAR NOT NULL,
    price MONEY
);

CREATE TABLE person
(
    id INTEGER primary key,
    name VARCHAR NOT NULL,
    age INTEGER CHECK ( age >0 ),
    driverLicense BOOLEAN,
    carsId INTEGER REFERENCES car (id)
);