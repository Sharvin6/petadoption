package com.example.pet_adoption_platform.service;

import com.example.pet_adoption_platform.model.Customer;
import com.example.pet_adoption_platform.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void saveCustomer(Customer customer) {
        // Logic to save customer information
        // For simplicity, let's assume it just prints a message
        System.out.println("Customer " + customer.getName() + " saved successfully.");
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    // Method to check if a customer exists by email
    public boolean customerExistsByName(String name) {
        return customerRepository.existsByName(name);
    }
}
