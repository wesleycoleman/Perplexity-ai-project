package com.example.petdatabase.service;
import com.example.petdatabase.dto.CreatePetRequest;
import com.example.petdatabase.dto.PetDTO;
import com.example.petdatabase.dto.PetNameBreedDto;
import com.example.petdatabase.entity.Pet;

import java.util.List;


public interface PetService {
    Pet createPet(Pet pet, String householdEircode);
    List<Pet> getAllPets();
    Pet getPetById(Long id);
    Pet updatePet(Long id, Pet pet, String householdEircode);
    void deletePetById(Long id);
    void deletePetsByName(String name);
    List<Pet> getPetsByAnimalType(String animalType);
    List<Pet> getPetsByBreed(String breed);
    List<PetNameBreedDto> getPetNameAndBreed();
    double getAverageAge();
    int getOldestAge();
    PetDTO createPet(CreatePetRequest request);
    PetDTO changePetName(Long id, String newName);

    List<PetDTO> findPetsByAnimalType(String animalType);
}