package com.example.petdatabase.dto;

import jakarta.validation.constraints.*;

public record CreatePetRequest(
        @NotBlank(message = "Pet name is required")
        String name,
        @NotBlank(message = "Animal type is required")
        String animalType,
        @NotBlank(message = "Breed is required")
        String breed,
        @Min(value = 0, message = "Age must be non-negative")
        int age,
        @NotBlank(message = "Household eircode is required")
        String householdEircode
) {
}
