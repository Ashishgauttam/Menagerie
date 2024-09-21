package com.example.Menagerie.controller;

import com.example.Menagerie.entities.Event;
import com.example.Menagerie.entities.Pet;
import com.example.Menagerie.response.ApiResponse;
import com.example.Menagerie.service.PetService;
import com.example.Menagerie.utils.ApiMessages;
import com.example.Menagerie.utils.ApiUrlPatterns;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApiUrlPatterns.MAIN_URL)
@Slf4j
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping(ApiUrlPatterns.PETS)
    public ResponseEntity<ApiResponse<List<Pet>>> getPetList(@RequestParam(required = false) String species) {
        log.info("Fetching pet list" + (species != null ? " for species: " + species : ""));
        List<Pet> pets = petService.getPetList(species);
        return new ResponseEntity<>(ApiResponse.<List<Pet>>builder()
                .msgCode(ApiMessages.DATA_FETCH.getCode())
                .message(ApiMessages.DATA_FETCH.getMessage())
                .results(pets).build(), HttpStatus.OK);
    }

    @GetMapping(ApiUrlPatterns.PET_BY_ID)
    public ResponseEntity<ApiResponse<Pet>> getPetByID(
            @PathVariable("id") Long id,
            @RequestParam(value = "sortKey", defaultValue = "eventDate") String sortKey,
            @RequestParam(value = "sortOrder", defaultValue = "desc") String sortOrder) {

        log.info("Fetching pet with id: {} and sorting events by {} in {} order", id, sortKey, sortOrder);
        Pet pet = petService.getPetByID(id, sortKey, sortOrder);

        return new ResponseEntity<>(ApiResponse.<Pet>builder()
                .msgCode(ApiMessages.DATA_FETCH.getCode())
                .message(ApiMessages.DATA_FETCH.getMessage())
                .results(pet).build(), HttpStatus.OK);
    }


    @PostMapping(ApiUrlPatterns.PETS)
    public ResponseEntity<ApiResponse<Pet>> addPetDetails(@Valid @RequestBody Pet pet, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessages = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new RuntimeException(errorMessages);
        }
        Pet savedPet = petService.addPetDetails(pet);
        return new ResponseEntity<>(ApiResponse.<Pet>builder()
                .msgCode(ApiMessages.DATA_ADDED.getCode())
                .message(ApiMessages.DATA_ADDED.getMessage())
                .results(savedPet).build(), HttpStatus.CREATED);
    }


    @PutMapping(ApiUrlPatterns.PET_BY_ID)
    public ResponseEntity<ApiResponse<Pet>> updatePetDetails(@PathVariable Long id, @Valid @RequestBody Pet pet, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessages = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new RuntimeException(errorMessages);
        }
        Pet updatedPet = petService.updatePetDetails(id, pet);
        return new ResponseEntity<>(ApiResponse.<Pet>builder()
                .msgCode(ApiMessages.DATA_UPDATED.getCode())
                .message(ApiMessages.DATA_UPDATED.getMessage())
                .results(updatedPet).build(), HttpStatus.OK);
    }


    @PostMapping(ApiUrlPatterns.ADD_EVENT_BY_ID)
    public ResponseEntity<ApiResponse<Event>> addEventToPet(@PathVariable Long id, @Valid @RequestBody Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessages = bindingResult.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new RuntimeException(errorMessages);
        }
        Event savedEvent = petService.addEventToPet(id, event);
        return new ResponseEntity<>(ApiResponse.<Event>builder()
                .msgCode(ApiMessages.EVENT_ADDED.getCode())
                .message(ApiMessages.EVENT_ADDED.getMessage())
                .results(savedEvent).build(), HttpStatus.CREATED);
    }


    @DeleteMapping(ApiUrlPatterns.DELETE)
    public ResponseEntity<ApiResponse<Object>> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return new ResponseEntity<>(ApiResponse.builder()
                .msgCode(ApiMessages.PET_DELETED.getCode())
                .message(ApiMessages.PET_DELETED.getMessage())
                .results(null).build(), HttpStatus.OK);
    }
}
