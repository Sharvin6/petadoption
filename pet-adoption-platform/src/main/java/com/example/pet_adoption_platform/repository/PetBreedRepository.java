package com.example.pet_adoption_platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pet_adoption_platform.model.PetBreed;
import com.example.pet_adoption_platform.model.PetType;

public interface PetBreedRepository extends JpaRepository<PetBreed, Integer> {
    List<PetBreed> findByPetType(PetType petType);
}

