-- run with psql -U postgres -d car_rental -f ./path/To/File/triggers.sql

\c car_rental

CREATE OR REPLACE FUNCTION check_car_availability()
RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT in_rental FROM car_rental.cars WHERE car_id = NEW.car_id) = true THEN
        RAISE EXCEPTION 'Car is already in rental.';
    END IF;
    
    UPDATE car_rental.cars
    SET in_rental = true
    WHERE car_id = NEW.car_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_car_availability
BEFORE INSERT ON car_rental.rentals
FOR EACH ROW EXECUTE FUNCTION check_car_availability();

CREATE OR REPLACE FUNCTION update_car_availability()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE car_rental.cars
    SET in_rental = false
    WHERE car_id = OLD.car_id;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_car_availability
AFTER DELETE OR UPDATE OF rental_id ON car_rental.rentals
FOR EACH ROW EXECUTE FUNCTION update_car_availability();