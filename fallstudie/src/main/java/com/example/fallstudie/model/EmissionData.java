package com.example.fallstudie.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "emissions")
public class EmissionData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;

    private int year;

    private double co2kt;

    private boolean approved;

    private String submittedBy;

    private LocalDateTime submittedAt;

    public EmissionData() {}

    public EmissionData(String country, int year, double co2kt) {
        this.country = country;
        this.year = year;
        this.co2kt = co2kt;
        this.approved = false;
    }

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getCo2kt() { return co2kt; }
    public void setCo2kt(double co2kt) { this.co2kt = co2kt; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }

    public String getSubmittedBy() { return submittedBy; }
    public void setSubmittedBy(String submittedBy) { this.submittedBy = submittedBy; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
}
