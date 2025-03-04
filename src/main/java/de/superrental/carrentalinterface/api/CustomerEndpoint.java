package de.superrental.carrentalinterface.api;

import de.superrental.carrentalinterface.dto.CustomerDTO;
import de.superrental.carrentalinterface.model.Customer;
import de.superrental.carrentalinterface.service.CustomerService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerEndpoint.class);
    private final CustomerService customerService;

    public CustomerEndpoint(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns list of all current customers.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = List.class)))
    })
    @GetMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> getCustomers() {
        LOGGER.info("Retrieving all current customers");
        return this.customerService.getAllCustomers();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added customer."),
            @ApiResponse(responseCode = "409", description = "Customer ID was set for creation!"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping(value = "/customers")
    public ResponseEntity<Void> addCustomer(@RequestBody CustomerDTO customerDTO) {
        if (customerDTO.customerId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        LOGGER.info("Adding customer: {}", customerDTO);
        return this.customerService.saveCustomer(customerDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Updated customer."),
            @ApiResponse(responseCode = "400", description = "Bad Request. Maybe no Customer ID was set.")
    })
    @PutMapping(value = "/customers")
    public ResponseEntity<Void> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        if (customerDTO.customerId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("Updating customer: {}", customerDTO);
        return this.customerService.saveCustomer(customerDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed customer."),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @DeleteMapping(value = "/customers")
    public ResponseEntity<Void> deleteCustomer(@RequestBody Long customerId) {
        LOGGER.info("Delete customer with id: {}", customerId);
        return this.customerService.deleteCustomer(customerId);
    }
}
