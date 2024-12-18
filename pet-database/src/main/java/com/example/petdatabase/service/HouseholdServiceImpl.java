package com.example.petdatabase.service;

import com.example.petdatabase.dto.CreateHouseholdRequest;
import com.example.petdatabase.dto.HouseholdDTO;
import com.example.petdatabase.entity.Household;
import com.example.petdatabase.entity.Pet;
import com.example.petdatabase.repository.HouseholdRepository;
import com.example.petdatabase.repository.PetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public abstract class HouseholdServiceImpl implements HouseholdService {
    private final HouseholdRepository householdRepository;
    private PetRepository petRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public HouseholdServiceImpl(HouseholdRepository householdRepository, PetRepository petRepository) {
        this.householdRepository = householdRepository;
        this.petRepository = petRepository;
    }

    @Override
    public HouseholdDTO createHousehold(CreateHouseholdRequest request) {
        Household household = modelMapper.map(request, Household.class);
        Household savedHousehold = householdRepository.save(household);
        return modelMapper.map(savedHousehold, HouseholdDTO.class);
    }

    @Override
    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    @Override
    public Optional<Household> findHouseholdByEircode(String eircode) {
        return householdRepository.findById(eircode);
    }

    @Override
    public Optional<Household> findHouseholdByEircodeWithPets(String eircode) {
        return householdRepository.findByEircodeWithPets(eircode);
    }

    @Override
    @Transactional
    public Household updateHousehold(String eircode, Household household) {
        validateHousehold(household);
        Household existingHousehold = householdRepository.findById(eircode)
                .orElseThrow(() -> new RuntimeException("Household not found"));
        existingHousehold.setNumberOfOccupants(household.getNumberOfOccupants());
        existingHousehold.setMaxNumberOfOccupants(household.getMaxNumberOfOccupants());
        existingHousehold.setOwnerOccupied(household.isOwnerOccupied());
        return householdRepository.save(existingHousehold);
    }

    @Override
    @Transactional
    public void deleteHouseholdByEircode(String eircode) {
        Household household = householdRepository.findById(eircode)
                .orElseThrow(() -> new RuntimeException("Household not found"));
        petRepository.deleteAll(household.getPets());
        householdRepository.delete(household);
    }

    @Override
    @Transactional
    public void deletePetsByName(String name) {
        List<Pet> petsToDelete = petRepository.findByNameIgnoreCase(name);
        petRepository.deleteAll(petsToDelete);
    }

    @Override
    public List<Household> findHouseholdsWithNoPets() {
        return householdRepository.findHouseholdsWithNoPets();
    }

    @Override
    public List<Household> findOwnerOccupiedHouseholds() {
        return householdRepository.findByOwnerOccupied(true);
    }

    @Override
    public long getEmptyHouseholdsCount() {
        return householdRepository.countEmptyHouseholds();
    }

    @Override
    public long getFullHouseholdsCount() {
        return householdRepository.countFullHouseholds();
    }

    private void validateHousehold(Household household) {
        if (household.getNumberOfOccupants() < 0) {
            throw new IllegalArgumentException("Number of occupants cannot be negative");
        }
        if (household.getMaxNumberOfOccupants() < 1) {
            throw new IllegalArgumentException("Maximum number of occupants must be at least 1");
        }
        if (household.getNumberOfOccupants() > household.getMaxNumberOfOccupants()) {
            throw new IllegalArgumentException("Number of occupants cannot exceed maximum number of occupants");
        }
    }
}
