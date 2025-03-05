-- run with psql -U postgres -d car_rental -f ./path/To/File/data.sql

\c car_rental

INSERT INTO car_rental.customers (first_name, last_name, email)
VALUES
	('Max', 'Mustermann', 'max.mustermann@business-beat.rocks');
	
INSERT INTO car_rental.cars(brand, model, in_rental)
VALUES
	('Seat', 'Arosa', true),
	('Honda', 'Accord', false),
	('Citroen', 'C1', false);
	
INSERT INTO car_rental.rentals(customer_id, car_id, mileage)
VALUES
	(1, 1, 10000);