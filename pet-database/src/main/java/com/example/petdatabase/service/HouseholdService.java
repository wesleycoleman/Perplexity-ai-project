package com.example.petdatabase.service;

import com.example.petdatabase.dto.CreateHouseholdRequest;
import com.example.petdatabase.dto.HouseholdDTO;
import com.example.petdatabase.entity.Household;

import java.util.List;
import java.util.Optional;

public interface HouseholdService {

    List<Household> getAllHouseholds();
    Optional<Household> findHouseholdByEircode(String eircode);
    Optional<Household> findHouseholdByEircodeWithPets(String eircode);
    Household updateHousehold(String eircode, Household household);
    void deleteHouseholdByEircode(String eircode);
    void deletePetsByName(String name);
    List<Household> findHouseholdsWithNoPets();
    List<Household> findOwnerOccupiedHouseholds();
    long getEmptyHouseholdsCount();
    long getFullHouseholdsCount();
    HouseholdDTO createHouseholdDTO(CreateHouseholdRequest request);
    HouseholdDTO createHousehold(CreateHouseholdRequest request);
}
