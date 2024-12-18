package com.example.petdatabase.entity;

import jakarta.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // Email

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Collection<String> roles; // List of roles

    private boolean locked; // Flag to indicate if the user is locked

    private String firstName;
    private String lastName;

    @Column(nullable = false)
    private String county; // County (e.g., 'Cork', 'Kerry')

    // Getters and Setters
}
