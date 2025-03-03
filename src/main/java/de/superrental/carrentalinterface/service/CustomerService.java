package de.superrental.carrentalinterface.service;

import de.superrental.carrentalinterface.dto.CustomerDTO;
import de.superrental.carrentalinterface.exception.CustomerNotFoundException;
import de.superrental.carrentalinterface.model.Customer;
import de.superrental.carrentalinterface.model.Rental;
import de.superrental.carrentalinterface.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;
    private final RentalService rentalService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, @Lazy RentalService rentalService) {
        this.customerRepository = customerRepository;
        this.rentalService = rentalService;
    }

    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    public Customer getCustomerById(Long customerId) throws CustomerNotFoundException {
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer with id " + customerId + " not found"));
    }

    public ResponseEntity<Void> saveCustomer(CustomerDTO customerDTO) {
        try {
            Customer customer = new Customer(customerDTO);
            customerRepository.save(customer);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to save customer. {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Void> deleteCustomer(Long customerId) {
        try {
            customerRepository.deleteById(customerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to delete customer. {}", e.getMessage());
            LOGGER.info("Trying to delete all rentals with customer {}", customerId);
            List<Rental> rentalsWithCustomerId = rentalService.getAllRentalsByCustomerId(customerId);
            if (!rentalsWithCustomerId.isEmpty()) {
                rentalsWithCustomerId.forEach(rental -> rentalService.deleteRental(rental.getRentalId()));
                customerRepository.deleteById(customerId);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
