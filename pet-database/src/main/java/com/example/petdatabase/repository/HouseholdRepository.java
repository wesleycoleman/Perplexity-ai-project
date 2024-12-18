package com.example.petdatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.petdatabase.entity.Household;


import java.util.List;
import java.util.Optional;

public interface HouseholdRepository extends JpaRepository<Household, String> {

    @Query("SELECT h FROM Household h LEFT JOIN FETCH h.pets WHERE h.eircode = :eircode")
    Optional<Household> findByEircodeWithPets(@Param("eircode") String eircode);

    @Query("SELECT h FROM Household h WHERE h.pets IS EMPTY")
    List<Household> findHouseholdsWithNoPets();

    List<Household> findByOwnerOccupied(boolean ownerOccupied);

    @Query("SELECT COUNT(h) FROM Household h WHERE h.numberOfOccupants = 0")
    long countEmptyHouseholds();

    @Query("SELECT COUNT(h) FROM Household h WHERE h.numberOfOccupants = h.maxNumberOfOccupants")
    long countFullHouseholds();
}
