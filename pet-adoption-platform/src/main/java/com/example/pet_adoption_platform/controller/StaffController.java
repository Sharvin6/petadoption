package com.example.pet_adoption_platform.controller;

import com.example.pet_adoption_platform.model.Staff;
import com.example.pet_adoption_platform.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping("/staff-login")
    public String showLoginPage() {
        return "staff-login";
    }

    @PostMapping("/staff-login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model) {
        Staff staff = staffService.authenticate(username, password);
        if (staff != null) {
            model.addAttribute("staffList", staffService.getAllStaff());
            return "redirect:/staff/management";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "staff-login";
        }
    }

    @GetMapping("/staff/management")
    public String showManagementPage() {
        return "staff-management";
    }

    @GetMapping("/staff/list")
    public String listStaff(Model model) {
        model.addAttribute("staffList", staffService.getAllStaff());
        return "staff-list";
    }

    @GetMapping("/staff/create")
    public String showCreateStaffForm(Model model) {
        model.addAttribute("staff", new Staff());
        return "create-staff";
    }

    @PostMapping("/staff/create")
    public String createStaff(@ModelAttribute("staff") Staff staff, Model model) {
        staffService.saveStaff(staff);
        return "redirect:/staff/list";
    }

    @GetMapping("/staff/edit")
    public String showEditList(Model model) {
        model.addAttribute("staffList", staffService.getAllStaff());
        return "staff-edit-list";
    }

    @GetMapping("/staff/edit/{id}c")
    public String showEditPage(@PathVariable("id") Long id, Model model) {
        Staff staff = staffService.getStaffById(id);
        if (staff != null) {
            model.addAttribute("staff", staff);
            return "staff-edit";
        } else {
            return "redirect:/staff/edit";
        }
    }

    @PostMapping("/staff/edit/{id}")
    public String editStaff(@PathVariable("id") Long id,
                            @RequestParam("name") String name,
                            @RequestParam("username") String username,
                            @RequestParam("email") String email,
                            @RequestParam("phone") String phone) {
        Staff staff = staffService.getStaffById(id);
        if (staff != null) {
            staff.setName(name);
            staff.setUsername(username);
            staff.setEmail(email);
            staff.setPhone(phone);
            staffService.saveStaff(staff);
        }
        return "redirect:/staff/edit";
    }

    @GetMapping("/staff/delete")
    public String showDeletePage(Model model) {
        model.addAttribute("staffList", staffService.getAllStaff());
        return "staff-delete";
    }

    @PostMapping("/staff/delete/{id}")
    public String deleteStaff(@PathVariable("id") Long id) {
        staffService.deleteStaffById(id);
        return "redirect:/staff/delete";
    }

    

}