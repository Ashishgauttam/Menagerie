package com.example.Menagerie.serviceImpl;

import com.example.Menagerie.entities.Event;
import com.example.Menagerie.entities.Pet;
import com.example.Menagerie.repository.EventRepository;
import com.example.Menagerie.repository.PetRepository;
import com.example.Menagerie.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Pet> getPetList(String species) {
        log.info("Fetching all pets");
        return (species == null) ? petRepository.findAll() : petRepository.findBySpecies(species);
    }

    @Override
    public Pet getPetByID(Long id, String sortKey, String sortOrder) {
        log.info("Fetching pet with id: {}", id);

        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
        List<Event> sortedEvents = pet.getEvents().stream()
                .sorted((e1, e2) -> {
                    Comparator<Event> comparator = Comparator.comparing(event -> {
                        if ("eventDate".equals(sortKey)) {
                            return event.getDate();
                        } // we can write more conditions if required
                        return event.getDate();
                    });
                    if ("desc".equalsIgnoreCase(sortOrder)) {
                        comparator = comparator.reversed();
                    }
                    return comparator.compare(e1, e2);
                })
                .collect(Collectors.toList());
        pet.setEvents(sortedEvents);

        return pet;
    }


    @Override
    public Pet addPetDetails(Pet pet) {
        log.info("Adding new pet: {}", pet.getName());
        pet.getEvents().forEach(event -> event.setPet(pet));
        return petRepository.save(pet);
    }

    @Override
    public Pet updatePetDetails(Long id, Pet pet) {
        log.info("Updating pet with id: {}", id);
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
        existingPet.setName(pet.getName());
        existingPet.setOwner(pet.getOwner());
        existingPet.setSpecies(pet.getSpecies());
        existingPet.setSex(pet.getSex());
        existingPet.setBirth(pet.getBirth());
        existingPet.setDeath(pet.getDeath());

        existingPet.getEvents().clear();
        if (pet.getEvents() != null) {
            for (Event event : pet.getEvents()) {
                event.setPet(existingPet);
                existingPet.getEvents().add(event);
            }
        }
        return petRepository.save(existingPet);
    }


    @Override
    public Event addEventToPet(Long petId, Event event) {
        log.info("Adding event to pet with id: {}", petId);
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + petId));
        event.setPet(pet);
        return eventRepository.save(event);
    }


    @Override
    public void deletePet(Long petId) {
        log.info("Deleting pet with id: {}", petId);
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: " + petId));
        petRepository.delete(pet);
    }
}
