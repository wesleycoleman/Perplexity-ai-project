package com.example.petdatabase.controllers;

import com.example.petdatabase.dto.CreateHouseholdRequest;
import com.example.petdatabase.dto.HouseholdDTO;
import com.example.petdatabase.dto.HouseholdInput;
import com.example.petdatabase.dto.PetDTO;
import com.example.petdatabase.entity.Household;
import com.example.petdatabase.entity.Pet;
import com.example.petdatabase.service.HouseholdService;
import com.example.petdatabase.service.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;


@Controller
public class GraphQLController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HouseholdService householdService;

    @Autowired
    private PetService petService;

    @QueryMapping
    public List<HouseholdDTO> getAllHouseholds() {
        return householdService.getAllHouseholds().stream()
                .map(household -> modelMapper.map(household, HouseholdDTO.class))
                .collect(Collectors.toList());
    }

    @QueryMapping
    public List<PetDTO> getPetsByAnimalType(@Argument String animalType) {
        return petService.findPetsByAnimalType(animalType);
    }

    @QueryMapping
    public HouseholdDTO getHousehold(@Argument String eircode) {
        Household household = householdService.findHouseholdByEircode(eircode).orElse(null);
        return household != null ? modelMapper.map(household, HouseholdDTO.class) : null;
    }

    @QueryMapping
    public PetDTO getPet(@Argument Long id) {
        Pet pet = petService.getPetById(id);
        return modelMapper.map(pet, PetDTO.class);
    }

    @QueryMapping
    public Map<String, Long> getStatistics() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("emptyHouses", householdService.getEmptyHouseholdsCount());
        stats.put("fullHouses", householdService.getFullHouseholdsCount());
        return stats;
    }

    @MutationMapping
    public HouseholdDTO createHousehold(@Argument HouseholdInput input) {
        CreateHouseholdRequest request = modelMapper.map(input, CreateHouseholdRequest.class);
        return householdService.createHousehold(request);
    }

    @MutationMapping
    public boolean deleteHousehold(@Argument String eircode) {
        householdService.deleteHouseholdByEircode(eircode);
        return true;
    }

    @MutationMapping
    public boolean deletePet(@Argument Long id) {
        petService.deletePetById(id);
        return true;
    }

    @QueryMapping
    public List<PetDTO> getAllPets() {
        // Accessible to all
    }

    @MutationMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public PetDTO updatePet(@Argument Long id, @Argument PetInput input) {
        
        // Only authenticated users can edit pets
    }

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public boolean deletePet(@Argument Long id) {
        petService.deletePetById(id);
        return true;
        // Only ADMIN can delete pets
    }


}
