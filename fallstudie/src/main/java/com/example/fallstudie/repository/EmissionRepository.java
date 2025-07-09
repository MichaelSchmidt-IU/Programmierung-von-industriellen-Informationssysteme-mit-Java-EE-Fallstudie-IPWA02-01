package com.example.fallstudie.repository;

import com.example.fallstudie.model.EmissionData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmissionRepository extends JpaRepository<EmissionData, Long> {

    List<EmissionData> findByCountry(String country);

    List<EmissionData> findByApprovedTrue();

    List<EmissionData> findByApprovedFalse();

    
    List<EmissionData> findBySubmittedByAndApprovedFalse(String submittedBy);
}
