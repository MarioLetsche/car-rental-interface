package de.superrental.carrentalinterface.dto;

public record CustomerDTO(
        String firstName,
        String lastName,
        String email,
        Long customerId
)
{}
