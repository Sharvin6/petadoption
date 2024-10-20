package com.example.pet_adoption_platform.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pet_adoption_platform.model.PetType;

public interface PetTypeRepository extends JpaRepository<PetType, Integer> {
}

