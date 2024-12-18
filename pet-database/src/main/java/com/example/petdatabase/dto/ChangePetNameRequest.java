package com.example.petdatabase.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangePetNameRequest(
        @NotBlank(message = "New name is required")
        String name
) {
        public String getName() {
                return name;
        }
}
