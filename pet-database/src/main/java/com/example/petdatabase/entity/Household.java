package com.example.petdatabase.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "household")
@Data
public class Household {
    @Id
    private String eircode;

    @Column(name = "number_of_occupants")
    private int numberOfOccupants;

    @Column(name = "max_number_of_occupants")
    private int maxNumberOfOccupants;

    @Column(name = "owner_occupied")
    private boolean ownerOccupied;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pet> pets;

    public int getNumberOfOccupants() {
        return numberOfOccupants;
    }

    public int getMaxNumberOfOccupants() {
        return maxNumberOfOccupants;
    }

    public boolean isOwnerOccupied() {
        return ownerOccupied;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setNumberOfOccupants(int numberOfOccupants) {
        this.numberOfOccupants = numberOfOccupants;
    }

    public void setMaxNumberOfOccupants(int maxNumberOfOccupants) {
        this.maxNumberOfOccupants = maxNumberOfOccupants;
    }

    public void setOwnerOccupied(boolean ownerOccupied) {
        this.ownerOccupied = ownerOccupied;
    }

}