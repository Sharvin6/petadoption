package com.example.pet_adoption_platform.repository;

import com.example.pet_adoption_platform.model.Pet;
import com.example.pet_adoption_platform.model.PetBreed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findByPetBreed(PetBreed petBreed);
    @Query("SELECT MAX(p.id) FROM Pet p")
    Optional<Integer> findMaxId();

}

