package com.example.petdatabase.dto;


import lombok.Value;

@Value
public class PetNameBreedDto {
    String name;
    String animalType;
    String breed;

    public PetNameBreedDto(String name, String animalType, String breed) {
        this.name = name;
        this.animalType = animalType;
        this.breed = breed;
    }
}