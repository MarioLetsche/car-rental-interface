package de.superrental.carrentalinterface;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Car Rental Interface",
                description = "REST Interface for car rentals",
                contact = @Contact(
                        name = "Super Rental",
                        email = "info@super-rental.de"
                )),
        servers = {
                @Server(url = "http://localhost:8080/car-rental-interface")
        }
)

@SpringBootApplication
public class CarRentalInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalInterfaceApplication.class, args);
    }

}
