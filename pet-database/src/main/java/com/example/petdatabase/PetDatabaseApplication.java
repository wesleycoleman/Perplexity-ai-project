package com.example.petdatabase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan({"com.example.petdatabase", "com.example.petdatabase.service"})
public class PetDatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetDatabaseApplication.class, args);
    }

}
