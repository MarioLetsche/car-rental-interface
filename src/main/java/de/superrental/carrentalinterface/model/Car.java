package de.superrental.carrentalinterface.model;

import de.superrental.carrentalinterface.dto.CarDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cars", schema = "car_rental")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;

    @Column(nullable = false, length = 25)
    private String brand;

    @Column(nullable = false, length = 25)
    private String model;

    @Column(nullable = false)
    private Boolean inRental;

    public Car(CarDTO carDTO) {
        this.brand = carDTO.brand();
        this.model = carDTO.model();
        this.inRental = carDTO.inRental();
        this.carId = carDTO.carId();
    }
}
