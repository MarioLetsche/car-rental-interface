package de.superrental.carrentalinterface.model;

import de.superrental.carrentalinterface.dto.CustomerDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers", schema = "car_rental")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @Column(nullable = false, length = 25)
    private String firstName;

    @Column(nullable = false, length = 25)
    private String lastName;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    public Customer(CustomerDTO customerDTO) {
        this.firstName = customerDTO.firstName();
        this.lastName = customerDTO.lastName();
        this.email = customerDTO.email();
        this.customerId = customerDTO.customerId();
    }
}
