package com.example.pet_adoption_platform.repository;

import com.example.pet_adoption_platform.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findByUsernameAndPassword(String username, String password);
}
