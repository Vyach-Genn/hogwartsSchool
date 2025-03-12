CREATE TABLE car
(
    id        SERIAL PRIMARY KEY,
    car_brand TEXT NOT NULL,
    car_model TEXT NOT NULL,
    car_price INTEGER CHECK ( car_price > 0 )
);

CREATE TABLE human
(
    id             SERIAL PRIMARY KEY,
    full_name      TEXT NOT NULL,
    age            INTEGER CHECK ( age >= 16 ),
    having_license BOOLEAN DEFAULT FALSE,
    id_car         INTEGER REFERENCES car (id)
);



