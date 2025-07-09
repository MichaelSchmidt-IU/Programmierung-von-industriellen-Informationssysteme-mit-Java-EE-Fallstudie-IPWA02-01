package com.example.fallstudie.controller;

import com.example.fallstudie.model.EmissionData;
import com.example.fallstudie.model.User;
import com.example.fallstudie.repository.EmissionRepository;
import com.example.fallstudie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ReviewController {

    private final EmissionRepository emissionRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewController(EmissionRepository emissionRepository, UserRepository userRepository) {
        this.emissionRepository = emissionRepository;
        this.userRepository = userRepository;
    }

    
    @GetMapping("/admin/review")
    @PreAuthorize("hasRole('ADMIN')")
    public String showReviewPage(Model model) {
        List<EmissionData> pending = emissionRepository.findByApprovedFalse();
        model.addAttribute("pendingEntries", pending);
        return "emissions-review"; /
    }

    
    @PostMapping("/admin/review/approve/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<String> approveEntry(@PathVariable Long id) {
        Optional<EmissionData> optional = emissionRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(404).body("Datensatz nicht gefunden");
        }

        EmissionData entry = optional.get();
        if (entry.isApproved()) {
            return ResponseEntity.status(409).body("Bereits freigegeben");
        }

        entry.setApproved(true);
        emissionRepository.save(entry);
        System.out.printf("Freigegeben: %s (%d) [%d]%n", entry.getCountry(), entry.getYear(), entry.getId());

        return ResponseEntity.ok("Freigabe erfolgreich");
    }

    
    @PostMapping("/admin/review/approve/all")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> approveAllEntries() {
        List<EmissionData> unapproved = emissionRepository.findByApprovedFalse();
        int updatedCount = unapproved.size();

        if (updatedCount == 0) {
            return ResponseEntity.ok(Map.of(
                "message", "Keine Einträge zum Freigeben.",
                "updated", 0
            ));
        }

        unapproved.forEach(entry -> entry.setApproved(true));
        emissionRepository.saveAll(unapproved);

        System.out.printf("%d Einträge freigegeben.%n", updatedCount);

        return ResponseEntity.ok(Map.of(
            "message",  + updatedCount + " Einträge wurden freigegeben.",
            "updated", updatedCount
        ));
    }

    
    @DeleteMapping("/admin/review/reject/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<String> rejectEntry(@PathVariable Long id) {
        Optional<EmissionData> optional = emissionRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(404).body("Datensatz nicht gefunden");
        }

        emissionRepository.deleteById(id);
        System.out.println(" Abgelehnt & gelöscht: " + id);

        return ResponseEntity.ok("Eintrag gelöscht");
    }

    
    @GetMapping("/admin/review/pending/count")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<Map<String, Long>> getPendingCount() {
        long count = emissionRepository.findByApprovedFalse().size();
        return ResponseEntity.ok(Map.of("count", count));
    }

    
    @GetMapping("/user/review/pending")
    @PreAuthorize("hasAnyRole('USER','SCIENTIST','ADMIN')")
    public String showUserPendingEntries(Model model, Principal principal) {
        Optional<User> userOpt = userRepository.findByUsername(principal.getName());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }

        User user = userOpt.get();
        List<EmissionData> pendingEntries =
                emissionRepository.findBySubmittedByAndApprovedFalse(user.getUsername());
        model.addAttribute("pendingEntries", pendingEntries);
        return "user/my-pending-entries"; 
    }
}
