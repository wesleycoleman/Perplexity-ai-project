package com.example.petdatabase.service;

import com.example.petdatabase.dto.PetNameBreedDto;
import com.example.petdatabase.entity.Household;
import com.example.petdatabase.entity.Pet;
import com.example.petdatabase.exception.InvalidPetDataException;
import com.example.petdatabase.exception.PetNotFoundException;
import com.example.petdatabase.repository.HouseholdRepository;
import com.example.petdatabase.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class PetServiceImpl implements PetService {
    @Autowired
    private final PetRepository petRepository;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet createPet(Pet pet, String householdEircode) {
        validatePet(pet);
        Household household = householdRepository.findById(householdEircode)
                .orElseThrow(() -> new RuntimeException("Household not found"));
        pet.setHousehold(household);
        return petRepository.save(pet);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet not found with id: " + id));
    }

    @Override
    public Pet updatePet(Long id, Pet pet, String householdEircode) {
        validatePet(pet);
        Pet existingPet = getPetById(id);
        Household household = householdRepository.findById(householdEircode)
                .orElseThrow(() -> new RuntimeException("Household not found"));
        existingPet.setName(pet.getName());
        existingPet.setAnimalType(pet.getAnimalType());
        existingPet.setBreed(pet.getBreed());
        existingPet.setAge(pet.getAge());
        existingPet.setHousehold(household);
        return petRepository.save(existingPet);
    }

    @Override
    public void deletePetById(Long id) {
        if (!petRepository.existsById(id)) {
            throw new PetNotFoundException("Pet not found with id: " + id);
        }
        petRepository.deleteById(id);
    }

    @Override
    public void deletePetsByName(String name) {
        petRepository.deleteByNameIgnoreCase(name);
    }

    @Override
    public List<Pet> getPetsByAnimalType(String animalType) {
        return petRepository.findByAnimalTypeIgnoreCase(animalType);
    }

    @Override
    public List<Pet> getPetsByBreed(String breed) {
        return petRepository.findByBreedIgnoreCaseOrderByAgeAsc(breed);
    }

    @Override
    public List<PetNameBreedDto> getPetNameAndBreed() {
        return petRepository.findAllNameAndBreed();
    }

    @Override
    public double getAverageAge() {
        Double averageAge = petRepository.getAverageAge();
        return averageAge != null ? averageAge : 0;
    }

    @Override
    public int getOldestAge() {
        Integer oldestAge = petRepository.getOldestAge();
        return oldestAge != null ? oldestAge : 0;
    }

    private void validatePet(Pet pet) {
        if (pet.getName() == null || pet.getName().trim().isEmpty()) {
            throw new InvalidPetDataException("Pet name cannot be empty");
        }
        if (pet.getAge() < 0) {
            throw new InvalidPetDataException("Pet age cannot be negative");
        }
    }
}
