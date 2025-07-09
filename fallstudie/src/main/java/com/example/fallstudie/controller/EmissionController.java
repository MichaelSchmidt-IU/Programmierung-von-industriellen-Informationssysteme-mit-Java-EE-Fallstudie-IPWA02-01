package com.example.fallstudie.controller;

import com.example.fallstudie.model.EmissionData;
import com.example.fallstudie.repository.EmissionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/emissions")
public class EmissionController {

    @Autowired
    private EmissionRepository emissionRepository;

   
    @GetMapping("/all")
    public List<EmissionData> getAllApproved() {
        return emissionRepository.findByApprovedTrue();
    }

    
    @PostMapping
    public ResponseEntity<EmissionData> addEmission(@Valid @RequestBody EmissionData data, Principal principal) {
        data.setApproved(false);
        data.setSubmittedAt(LocalDateTime.now());
        data.setSubmittedBy(principal != null ? principal.getName() : "unbekannt");
        return ResponseEntity.ok(emissionRepository.save(data));
    }

    
    @PostMapping("/bulk")
    public ResponseEntity<List<EmissionData>> bulkImport(@RequestBody List<EmissionData> list, Principal principal) {
        String user = principal != null ? principal.getName() : "unbekannt";
        LocalDateTime now = LocalDateTime.now();
        for (EmissionData d : list) {
            d.setApproved(false);
            d.setSubmittedBy(user);
            d.setSubmittedAt(now);
        }
        return ResponseEntity.ok(emissionRepository.saveAll(list));
    }

    
    @DeleteMapping("/admin/reject/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> rejectEntry(@PathVariable Long id) {
        Optional<EmissionData> entry = emissionRepository.findById(id);
        if (entry.isEmpty()) {
            return ResponseEntity.status(404).body(" Eintrag nicht gefunden");
        }

        emissionRepository.deleteById(id);
        return ResponseEntity.ok(" Eintrag gelöscht");
    }
}
