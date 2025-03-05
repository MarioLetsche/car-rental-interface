package de.superrental.carrentalinterface.dto;

public record CarDTO(
        String brand,
        String model,
        Boolean inRental,
        Integer carId
) {}
