package com.example.Menagerie.service;

import com.example.Menagerie.entities.Event;
import com.example.Menagerie.entities.Pet;

import java.util.List;

public interface PetService {
    List<Pet> getPetList(String species);

    Pet getPetByID(Long id, String sortKey, String sortOrder);

    Pet addPetDetails(Pet pet);

    Pet updatePetDetails(Long id, Pet pet);

    Event addEventToPet(Long petId, Event event);

    void deletePet(Long id);
}
