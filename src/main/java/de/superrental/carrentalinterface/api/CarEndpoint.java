package de.superrental.carrentalinterface.api;

import de.superrental.carrentalinterface.dto.CarDTO;
import de.superrental.carrentalinterface.model.Car;
import de.superrental.carrentalinterface.service.CarService;
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
public class CarEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarEndpoint.class);
    private final CarService carService;

    public CarEndpoint(final CarService carService) {
        this.carService = carService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns list of all current cars.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = List.class)))
    })
    @GetMapping(value = "/cars")
    public ResponseEntity<List<Car>> getCars() {
        LOGGER.info("Retrieving all cars");
        return carService.getAllCars();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added car."),
            @ApiResponse(responseCode = "409", description = "Car ID was set for creation!"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping(value = "/cars")
    public ResponseEntity<Void> addCar(@RequestBody CarDTO carDTO) {
        if (carDTO.carId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        LOGGER.info("Adding car {}", carDTO);
        return carService.addCar(carDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Updated car."),
            @ApiResponse(responseCode = "400", description = "Bad Request. Maybe Car ID was not set.")
    })
    @PutMapping(value = "/cars")
    public ResponseEntity<Void> updateCar(@RequestBody CarDTO carDTO) {
        if (carDTO.carId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("Updating car {}", carDTO);
        return carService.addCar(carDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed car.")
    })
    @DeleteMapping(value = "/cars")
    public ResponseEntity<Void> removeCar(@RequestBody Integer carId) {
        LOGGER.info("Removing car with id: {}", carId);
        return carService.deleteCar(carId);
    }
}
