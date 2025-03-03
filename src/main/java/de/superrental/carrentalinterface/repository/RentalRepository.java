package de.superrental.carrentalinterface.repository;

import de.superrental.carrentalinterface.model.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {
}
