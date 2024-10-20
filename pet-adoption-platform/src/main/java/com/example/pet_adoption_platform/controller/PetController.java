package com.example.pet_adoption_platform.controller;

import com.example.pet_adoption_platform.model.AdoptionForm;
import com.example.pet_adoption_platform.model.Pet;
import com.example.pet_adoption_platform.model.PetBreed;
import com.example.pet_adoption_platform.model.PetType;
import com.example.pet_adoption_platform.service.PetService;
import com.example.pet_adoption_platform.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class PetController {

    private final PetService petService;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/contact")
    public String showContactPage() {
        return "contact"; // Assuming your Thymeleaf template for the contact page is named "contact.html"
    }

    @GetMapping("/pets")
    public String getPets(Model model) {
        List<PetType> petTypes = petService.getAllPetTypes();
        for (PetType type : petTypes) {
            type.setBreeds(petService.getBreedsByType(type));
        }
        model.addAttribute("petTypes", petTypes);
        return "pets";
    }

    @GetMapping("/breed-details/{id}")
    public String getBreedDetails(@PathVariable("id") int id, Model model) {
        PetBreed breed = petService.getBreedById(id);
        List<Pet> pets = petService.getPetsByBreed(breed);
        model.addAttribute("breed", breed);
        model.addAttribute("pets", pets);
        return "breed-details";
    }

    @GetMapping("/adopt/{petId}")
    public String showAdoptionForm(@PathVariable int petId, Model model) {
        Pet pet = petService.getPetById(petId);
        if (pet == null) {
            return "error/404"; // Handle case where pet is not found
        }
        AdoptionForm adoptionForm = new AdoptionForm();
        adoptionForm.setCustomerId(IdGenerator.generateRandomId());
        model.addAttribute("pet", pet);
        model.addAttribute("adoptionForm", new AdoptionForm()); // Assuming AdoptionForm is your form backing object
        return "adopt-form"; // Ensure this matches your actual HTML file name
    }

    @GetMapping("/staff/listofpets")
    public String listPets(Model model) {
        List<Pet> pets = petService.getAllPets();
        model.addAttribute("pets", pets);
        return "list"; // Ensure you have a list.html template in src/main/resources/templates
    }

    @GetMapping("/staff/createpet")
    public String createPetForm(Model model) {
        model.addAttribute("pet", new Pet());
        model.addAttribute("petTypes", petService.getAllPetTypes());
        model.addAttribute("petBreeds", petService.getAllBreeds());
        return "create-pet";
    }

    @PostMapping("/staff/createpet")
    public String createPet(@RequestParam("name") String name,
                            @RequestParam("age") int age,
                            @RequestParam("gender") String gender,
                            @RequestParam("color") String color,
                            @RequestParam("description") String description,
                            @RequestParam("fee") double fee,
                            @RequestParam("petType") int petTypeId,
                            @RequestParam("petBreed") int petBreedId,
                            @RequestParam("image") MultipartFile image) {

        PetType petType = petService.getPetTypeById(petTypeId);
        PetBreed petBreed = petService.getBreedById(petBreedId);

        Pet pet = new Pet();
        pet.setName(name);
        pet.setAge(age);
        pet.setGender(gender);
        pet.setColor(color);
        pet.setDescription(description);
        pet.setFee(fee);
        pet.setPetType(petType);
        pet.setPetBreed(petBreed);

        if (!image.isEmpty()) {
            try {
                // Ensure the directory exists
                Path uploadDir = Paths.get(uploadPath);
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }
                
                // Save the uploaded file
                byte[] bytes = image.getBytes();
                Path path = uploadDir.resolve(image.getOriginalFilename());
                Files.write(path, bytes);
                pet.setImage_url("/images/" + image.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        petService.savePet(pet);
        return "redirect:/staff/listofpets";
    }

    @GetMapping("/pet-details")
    public String petDetailsPage(Model model) {
        List<Pet> pets = petService.getAllPets();
        model.addAttribute("pets", pets);
        return "pet-details";
    }

    @GetMapping("/edit-pet")
    public String editPetPage(@RequestParam("id") int id, Model model) {
        Pet pet = petService.getPetById(id);
        model.addAttribute("pet", pet);
        // Assuming you also need to pass petTypes and petBreeds to the edit-pet.html page
        model.addAttribute("petTypes", petService.getAllPetTypes());
        model.addAttribute("petBreeds", petService.getAllBreeds());
        return "edit-pet";
    }

    @PostMapping("/update-pet")
    public String updatePet(@ModelAttribute Pet pet, @RequestParam("image") MultipartFile image) {
        // Process image file if present
        if (!image.isEmpty()) {
            try {
                String filename = StringUtils.cleanPath(image.getOriginalFilename());
                Path uploadPath = Paths.get("src/main/resources/static/images");

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(filename);
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                pet.setImage_url("/images/" + filename); // Save the path to the database
            } catch (IOException e) {
                e.printStackTrace();
                // Handle error
            }


             }
             petService.savePet(pet);
             // Redirect to pet-details.html
             return "redirect:/pet-details";
             }

    // Mapping to display the delete pet page
    @GetMapping("/staff/delete-pet")
    public String showDeletePetPage(Model model) {
        List<Pet> pets = petService.getAllPets();
        model.addAttribute("pets", pets);
        return "delete-pet";
    }

    // Mapping to handle the deletion of pets
    @PostMapping("/staff/delete-pet")
    public String deletePet(@RequestParam("id") int id) {
        petService.deletePetById(id);
        return "redirect:/staff/delete-pet";
    }
    
}
