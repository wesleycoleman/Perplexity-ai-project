package com.example.petdatabase.dto;

import jakarta.validation.constraints.*;

public record PetDTO(Long id, String name, String animalType, String breed, int age) {

}
