package com.example.Menagerie.repository;

import com.example.Menagerie.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet,Long> {
    List<Pet> findBySpecies(String species);
}
