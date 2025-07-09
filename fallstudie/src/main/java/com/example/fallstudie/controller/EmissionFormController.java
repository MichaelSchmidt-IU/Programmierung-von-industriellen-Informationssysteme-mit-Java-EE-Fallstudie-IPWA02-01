package com.example.fallstudie.controller;

import com.example.fallstudie.model.EmissionData;
import com.example.fallstudie.model.User;
import com.example.fallstudie.repository.EmissionRepository;
import com.example.fallstudie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class EmissionFormController {

    private final EmissionRepository emissionRepository;
    private final UserRepository userRepository;

    @Autowired
    public EmissionFormController(EmissionRepository emissionRepository,
                                  UserRepository userRepository) {
        this.emissionRepository = emissionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/emissions/form")
    @PreAuthorize("hasAnyRole('USER','SCIENTIST','ADMIN')")
    public String showForm(Model model, Principal principal) {
        Optional<User> userOpt = userRepository.findByUsername(principal.getName());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }

        User user = userOpt.get();
        List<EmissionData> pendingEntries =
                emissionRepository.findBySubmittedByAndApprovedFalse(user.getUsername());

        model.addAttribute("pendingEntries", pendingEntries);
        return "emissions-form"; 
    }
}
