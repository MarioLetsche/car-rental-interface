package de.superrental.carrentalinterface.service;

import de.superrental.carrentalinterface.dto.CarDTO;
import de.superrental.carrentalinterface.exception.CarNotFoundException;
import de.superrental.carrentalinterface.model.Car;
import de.superrental.carrentalinterface.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);
    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = (List<Car>) carRepository.findAll();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    public Car getCarById(Integer carId) throws RuntimeException {
        return carRepository.findById(carId).orElseThrow(() -> new CarNotFoundException("Car with id " + carId + " not found"));
    }

    public ResponseEntity<Void> addCar(CarDTO carDTO) {
        try {
            Car car = new Car(carDTO);
            carRepository.save(car);
            if (carDTO.carId() == null) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to save car. {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Void> deleteCar(Integer carId) {
        try {
            carRepository.deleteById(carId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            LOGGER.error("Failed to delete car. {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
