package com.example.petdatabase.controllers;

import com.example.petdatabase.dto.CreateHouseholdRequest;
import com.example.petdatabase.dto.HouseholdDTO;
import com.example.petdatabase.entity.Household;
import com.example.petdatabase.service.HouseholdService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/households")
public class HouseholdController {

    @Autowired
    private HouseholdService householdService;
    @Autowired private ModelMapper modelMapper;

    @PostMapping // Create a new household
    public ResponseEntity<HouseholdDTO> createHousehold(@Valid @RequestBody CreateHouseholdRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(householdService.createHousehold(request));
    }

    @GetMapping("/households")
    public ResponseEntity<List<HouseholdDTO>> getAllHouseholds() {
        List<Household> households = householdService.getAllHouseholds();
        List<HouseholdDTO> householdDTOs = households.stream()
                .map(household -> modelMapper.map(household, HouseholdDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(householdDTOs);
    }

    @GetMapping("/{eircode}") // Get household by ID (with pets)
    public ResponseEntity<HouseholdDTO> getHouseholdWithPets(@PathVariable String eircode) {
        return householdService.findHouseholdByEircodeWithPets(eircode)
                .map(h -> modelMapper.map(h, HouseholdDTO.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/households/{eircode}")
    public ResponseEntity<Void> deleteHousehold(@PathVariable String eircode) {
        householdService.deleteHouseholdByEircode(eircode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/households/no-pets")
    public ResponseEntity<List<HouseholdDTO>> getHouseholdsWithNoPets() {
        List<Household> households = householdService.findHouseholdsWithNoPets();
        List<HouseholdDTO> householdDTOs = households.stream()
                .map(household -> modelMapper.map(household, HouseholdDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(householdDTOs);
    }


}
