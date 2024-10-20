package com.example.pet_adoption_platform.repository;

import com.example.pet_adoption_platform.model.AdoptedPet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptedPetRepository extends JpaRepository<AdoptedPet, Integer> {
    
}
