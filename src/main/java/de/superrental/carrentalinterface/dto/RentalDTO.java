package de.superrental.carrentalinterface.dto;

public record RentalDTO(
        Integer customerId,
        Integer carId,
        Integer mileage,
        Integer rentalId
) {}
