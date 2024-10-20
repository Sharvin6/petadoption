package com.example.pet_adoption_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pet_adoption_platform.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByName(String name);
}
