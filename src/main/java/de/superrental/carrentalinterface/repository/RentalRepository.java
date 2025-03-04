package de.superrental.carrentalinterface.repository;

import de.superrental.carrentalinterface.model.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {
    List<Rental> findByCustomer_customerId(Long customerId);
}
