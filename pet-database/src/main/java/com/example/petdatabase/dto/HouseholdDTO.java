package com.example.petdatabase.dto;

import jakarta.validation.constraints.*;

public record HouseholdDTO(String eircode, int numberOfOccupants, int maxNumberOfOccupants, boolean ownerOccupied) {

}