package com.example.petdatabase;

import com.example.petdatabase.entity.Pet;
import com.example.petdatabase.exception.InvalidPetDataException;
import com.example.petdatabase.exception.PetNotFoundException;
import com.example.petdatabase.repository.PetRepository;
import com.example.petdatabase.service.PetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl petService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPet_ValidPet_ReturnsSavedPet() {
        Pet pet = new Pet();
        pet.setName("Buddy");
        pet.setAge(3);
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        Pet savedPet = petService.createPet(pet, "D02XY45");

        assertNotNull(savedPet);
        assertEquals("Buddy", savedPet.getName());
        verify(petRepository).save(pet);
    }

    @Test
    void createPet_InvalidPet_ThrowsException() {
        Pet pet = new Pet();
        pet.setName("");
        pet.setAge(3);

        assertThrows(InvalidPetDataException.class, () -> petService.createPet(pet, "D02XY45"));
    }

    @Test
    void getPetById_ExistingId_ReturnsPet() {
        Long id = 1L;
        Pet pet = new Pet();
        pet.setId(id);
        when(petRepository.findById(id)).thenReturn(Optional.of(pet));

        Pet foundPet = petService.getPetById(id);

        assertNotNull(foundPet);
        assertEquals(id, foundPet.getId());
    }

    @Test
    void getPetById_NonExistingId_ThrowsException() {
        Long id = 1L;
        when(petRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> petService.getPetById(id));
    }

    @Test
    void getPetsByAnimalType_ReturnsFilteredList() {
        String animalType = "Dog";
        List<Pet> dogs = Arrays.asList(new Pet(), new Pet());
        when(petRepository.findByAnimalTypeIgnoreCase(animalType)).thenReturn(dogs);

        List<Pet> result = petService.getPetsByAnimalType(animalType);

        assertEquals(2, result.size());
        verify(petRepository).findByAnimalTypeIgnoreCase(animalType);
    }
}