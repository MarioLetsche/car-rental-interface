package de.superrental.carrentalinterface.dto;

public record RentalDTO(
        Long customerId,
        Long carId,
        Integer mileage,
        Long rentalId
) {}
