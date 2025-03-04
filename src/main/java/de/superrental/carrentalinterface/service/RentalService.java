package de.superrental.carrentalinterface.service;

import de.superrental.carrentalinterface.dto.RentalDTO;
import de.superrental.carrentalinterface.model.Car;
import de.superrental.carrentalinterface.model.Customer;
import de.superrental.carrentalinterface.model.Rental;
import de.superrental.carrentalinterface.repository.RentalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalService.class);
    private final RentalRepository rentalRepository;
    private final CustomerService customerService;
    private final CarService carService;

    @Autowired
    public RentalService(RentalRepository rentalRepository, @Lazy CustomerService customerService, CarService carService) {
        this.rentalRepository = rentalRepository;
        this.customerService = customerService;
        this.carService = carService;
    }

    public ResponseEntity<List<Rental>> getAllRentals() {
        List<Rental> rentals = (List<Rental>) rentalRepository.findAll();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    public List<Rental> getAllRentalsByCustomerId(Long customerId) {
        return rentalRepository.findByCustomer_customerId(customerId);
    }

    public ResponseEntity<Void> addRental(RentalDTO rentalDTO) {
        try {
            Customer customer = customerService.getCustomerById(rentalDTO.customerId());
            Car car = carService.getCarById(rentalDTO.carId());
            Rental rental = new Rental(customer, car, rentalDTO.mileage());
            rentalRepository.save(rental);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Void> deleteRental(Long rentalId) {
        rentalRepository.deleteById(rentalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
