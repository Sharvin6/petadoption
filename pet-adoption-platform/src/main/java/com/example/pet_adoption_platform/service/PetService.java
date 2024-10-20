package com.example.pet_adoption_platform.service;

import com.example.pet_adoption_platform.model.Pet;
import com.example.pet_adoption_platform.model.PetBreed;
import com.example.pet_adoption_platform.model.PetType;
import com.example.pet_adoption_platform.repository.PetBreedRepository;
import com.example.pet_adoption_platform.repository.PetRepository;
import com.example.pet_adoption_platform.repository.PetTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetTypeRepository petTypeRepository;
    private final PetBreedRepository petBreedRepository;
    private final PetRepository petRepository;

    public PetService(PetTypeRepository petTypeRepository, PetBreedRepository petBreedRepository, PetRepository petRepository) {
        this.petTypeRepository = petTypeRepository;
        this.petBreedRepository = petBreedRepository;
        this.petRepository = petRepository;
    }

    public List<PetType> getAllPetTypes() {
        return petTypeRepository.findAll();
    }

    public List<PetBreed> getBreedsByType(PetType type) {
        return petBreedRepository.findByPetType(type);
    }

    public PetBreed getBreedById(int id) {
        return petBreedRepository.findById(id).orElse(null);
    }

    public PetType getPetTypeById(int id) {
        return petTypeRepository.findById(id).orElse(null);
    }

    public List<Pet> getPetsByBreed(PetBreed breed) {
        return petRepository.findByPetBreed(breed);
    }

    public Pet getPetById(int id) {
        return petRepository.findById(id).orElse(null);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public void savePet(Pet pet) {
        petRepository.save(pet);
    }

    public List<PetBreed> getAllBreeds() {
        return petBreedRepository.findAll();
    }

    public int getNextPetId() {
        return petRepository.findMaxId().orElse(0) + 1;
    }

    public void deletePetById(int id) {
        petRepository.deleteById(id);
    }

   
}
