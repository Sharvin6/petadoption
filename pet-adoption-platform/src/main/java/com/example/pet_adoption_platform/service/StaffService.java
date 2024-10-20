package com.example.pet_adoption_platform.service;

import com.example.pet_adoption_platform.model.Staff;
import com.example.pet_adoption_platform.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    // Method to authenticate a staff member
    public Staff authenticate(String username, String password) {
        return staffRepository.findByUsernameAndPassword(username, password);
    }

    // Method to retrieve all staff members
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    // Method to retrieve a staff member by their ID
    public Staff getStaffById(Long id) {
        return staffRepository.findById(id).orElse(null);
    }

    // Method to save or update a staff member
    public void saveStaff(Staff staff) {
        staffRepository.save(staff);
    }

    public void deleteStaffById(Long id) {
        staffRepository.deleteById(id);
    }
}
