package com.example.petdatabase.dto;

import jakarta.validation.constraints.*;

public record ChangePetRequest(
        @NotBlank(message = "New name is required")
        String name
) {
}
