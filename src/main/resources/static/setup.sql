-- run with psql -U postgres -d car_rental -f ./path/To/File/setup.sql

\c car_rental

CREATE SCHEMA car_rental


CREATE TABLE car_rental.customers (
	customer_id SERIAL PRIMARY KEY,
	first_name VARCHAR(25) NOT NULL,
	last_name VARCHAR(25) NOT NULL,
	email VARCHAR (50) UNIQUE NOT NULL
);

CREATE TABLE car_rental.cars (
	car_id SERIAL PRIMARY KEY,
	brand  VARCHAR(25) NOT NULL,
	model VARCHAR(25) NOT NULL,
	in_rental BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE car_rental.rentals (
	rental_id SERIAL PRIMARY KEY,
	customer_id INT REFERENCES car_rental.customers(customer_id) NOT NULL,
	car_id INT REFERENCES car_rental.cars(car_id) NOT NULL,
	mileage INT NOT NULL,
	CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES car_rental.customers(customer_id) ON DELETE CASCADE,
    CONSTRAINT fk_car FOREIGN KEY (car_id) REFERENCES car_rental.cars(car_id) ON DELETE CASCADE
);

CREATE INDEX idx_rentals_customer ON car_rental.rentals(customer_id);
CREATE INDEX idx_rentals_car ON car_rental.rentals(car_id);
CREATE INDEX idx_customers_id ON car_rental.customers(customer_id);
CREATE INDEX idx_cars_id ON car_rental.cars(car_id);
CREATE INDEX idx_rentals ON car_rental.rentals(rental_id);