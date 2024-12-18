package com.example.petdatabase.exception;

public class InvalidPetDataException extends RuntimeException {
    public InvalidPetDataException(String message) {
        super(message);
    }
}