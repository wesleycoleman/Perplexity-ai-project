package com.example.petdatabase.dto;

import jakarta.validation.constraints.*;

public record CreateHouseholdRequest(
        @NotBlank(message = "Eircode is required")
        String eircode,
        @Min(value = 0, message = "Number of occupants must be non-negative")
        int numberOfOccupants,
        @Min(value = 1, message = "Max number of occupants must be at least 1")
        int maxNumberOfOccupants,
        boolean ownerOccupied
) {
}
