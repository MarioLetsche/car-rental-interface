package de.superrental.carrentalinterface.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rentals", schema = "car_rental")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rentalId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(nullable = false)
    private Integer mileage;

    public Rental(Customer customer, Car car, Integer mileage, Integer rentalId) {
        this.customer = customer;
        this.car = car;
        this.mileage = mileage;
        this.rentalId = rentalId;
    }
}
