package com.example.petdatabase.repository;

import com.example.petdatabase.dto.PetNameBreedDto;
import com.example.petdatabase.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    void deleteByNameIgnoreCase(String name);
    List<Pet> findByNameIgnoreCase(String name);
    List<Pet> findByAnimalTypeIgnoreCase(String name);
    List<Pet> findByBreedIgnoreCaseOrderByAgeAsc(String breed);

    @Query("SELECT new com.example.petdatabase.dto.PetNameBreedDto(p.name, p.animalType, p.breed) FROM Pet p")
    List<PetNameBreedDto> findAllNameAndBreed();

    @Query("SELECT AVG(p.age) FROM Pet p")
    Double getAverageAge();

    @Query("SELECT MAX(p.age) FROM Pet p")
    Integer getOldestAge();
}
