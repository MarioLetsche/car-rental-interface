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
            @ApiResponse(responseCode = "204", description = "Added or updated car."),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PutMapping(value = "/cars")
    public ResponseEntity<Void> addCar(@RequestBody CarDTO carDTO) {
        LOGGER.info("Adding or updating car {}", carDTO);
        return carService.addCar(carDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed car.")
    })
    @DeleteMapping(value = "/cars")
    public ResponseEntity<Void> removeCar(@RequestBody Long carId) {
        LOGGER.info("Removing car with id: {}", carId);
        return carService.deleteCar(carId);
    }
}
