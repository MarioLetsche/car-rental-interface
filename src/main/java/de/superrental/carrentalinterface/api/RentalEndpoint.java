package de.superrental.carrentalinterface.api;

import de.superrental.carrentalinterface.dto.RentalDTO;
import de.superrental.carrentalinterface.model.Rental;
import de.superrental.carrentalinterface.service.RentalService;
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
public class RentalEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalEndpoint.class);
    private final RentalService rentalService;

    public RentalEndpoint(final RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns list of all current rentals.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = List.class)))
    })
    @GetMapping(value = "/rentals")
    public ResponseEntity<List<Rental>> getRentals() {
        LOGGER.info("Retrieving all current running rentals");
        return rentalService.getAllRentals();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added rental."),
            @ApiResponse(responseCode = "409", description = "Rental ID was set during creation!"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping(value = "/rentals")
    public ResponseEntity<Void> addRental(@RequestBody RentalDTO rentalDTO) {
        if (rentalDTO.rentalId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        LOGGER.info("Adding rental {}", rentalDTO);
        return rentalService.addRental(rentalDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Updated rental."),
            @ApiResponse(responseCode = "400", description = "Bad Request. Maybe Rental ID was set during creation.")
    })
    @PutMapping(value = "/rentals")
    public ResponseEntity<Void> updateRental(@RequestBody RentalDTO rentalDTO) {
        if (rentalDTO.rentalId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("Updating rental {}", rentalDTO);
        return rentalService.addRental(rentalDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted rental.")
    })
    @DeleteMapping(value = "/rentals")
    public ResponseEntity<Void> removeRental(@RequestBody Integer rentalId) {
        LOGGER.info("Removing rental with id: {}", rentalId);
        return rentalService.deleteRental(rentalId);
    }
}
