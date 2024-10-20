package com.example.pet_adoption_platform.controller;

import com.example.pet_adoption_platform.model.AdoptedPet;
import com.example.pet_adoption_platform.model.AdoptionForm;
import com.example.pet_adoption_platform.model.Customer;
import com.example.pet_adoption_platform.model.Pet;
import com.example.pet_adoption_platform.service.AdoptionService;
import com.example.pet_adoption_platform.service.CustomerService;
import com.example.pet_adoption_platform.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class AdoptionController {

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdoptionService adoptionService;

    @GetMapping("/adopt-form/{petId}")
    public String showAdoptionForm(@PathVariable("petId") int petId, Model model) {
        Pet pet = petService.getPetById(petId);
        if (pet == null) {
            return "error/404"; // or any other error page
        }
        model.addAttribute("pet", pet);

        AdoptionForm adoptionForm = new AdoptionForm();
        adoptionForm.setCustomerId(UUID.randomUUID().toString().substring(0, 10)); // Generate a shorter random customer ID

        model.addAttribute("adoptionForm", adoptionForm);
        return "adopt-form";
    }

    @PostMapping("/submit-adoption")
    public String submitAdoptionForm(@ModelAttribute("adoptionForm") AdoptionForm adoptionForm, Model model) {
        Pet pet = petService.getPetById(adoptionForm.getPetId());

        Customer customer = new Customer();
        customer.setName(adoptionForm.getCustomerName());
        customer.setEmail(adoptionForm.getCustomerEmail());
        customer.setAddress(adoptionForm.getCustomerAddress());
        customer.setPhone(adoptionForm.getCustomerPhone());
        customer.setId(adoptionForm.getCustomerId()); // Set the random customer ID

        customerService.saveCustomer(customer);
        adoptionService.saveAdoption(customer, pet);

        // Generate a random invoice ID for this adoption
        int invoiceId = UUID.randomUUID().toString().substring(0, 10).hashCode();

        // Pass necessary data to invoice.html
        model.addAttribute("pet", pet);
        model.addAttribute("customer", customer);
        model.addAttribute("invoiceId", invoiceId);
        model.addAttribute("date", "January 1, 2024");

        // Redirect to invoice.html
        return "invoice";
    }

    @GetMapping("/customer-adoption-details")
    public String getCustomerAdoptionDetails(Model model) {
        List<AdoptedPet> adoptions = adoptionService.getAllAdoptedPets();
        model.addAttribute("adoptions", adoptions);
        return "customer-adoption-details";
    }

    
}
