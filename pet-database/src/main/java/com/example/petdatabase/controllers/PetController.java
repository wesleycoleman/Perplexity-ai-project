package com.example.petdatabase.controllers;

import com.example.petdatabase.dto.ChangePetNameRequest;
import com.example.petdatabase.dto.CreatePetRequest;
import com.example.petdatabase.dto.PetDTO;
import com.example.petdatabase.entity.Pet;
import com.example.petdatabase.service.PetService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;
    @Autowired private ModelMapper modelMapper;

    @PostMapping // Create a new pet
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PetDTO> createPet(@Valid @RequestBody CreatePetRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.createPet(request));
    }

    @GetMapping("/pets")
    public ResponseEntity<List<PetDTO>> getAllPets() {
        List<Pet> pets = petService.getAllPets();
        List<PetDTO> petDTOs = pets.stream()
                .map(pet -> modelMapper.map(pet, PetDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(petDTOs);
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<PetDTO> getPet(@PathVariable Long id) {
        Pet pet = petService.getPetById(id);
        PetDTO petDTO = modelMapper.map(pet, PetDTO.class);
        return ResponseEntity.ok(petDTO);
    }

    @PatchMapping("/pets/{id}/name")
    public ResponseEntity<PetDTO> changePetName(@PathVariable Long id, @Valid @RequestBody ChangePetNameRequest request) {
        return ResponseEntity.ok(petService.changePetName(id, request.getName()));
    }

    @DeleteMapping("/pets/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePetById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/name/{name}") // Delete pets by name (ignoring case)
    public ResponseEntity<Void> deletePetsByName(@PathVariable String name) {
        petService.deletePetsByName(name);
        return ResponseEntity.noContent().build();
    }
}
