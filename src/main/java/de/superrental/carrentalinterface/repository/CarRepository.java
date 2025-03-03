package de.superrental.carrentalinterface.repository;

import de.superrental.carrentalinterface.model.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
}
