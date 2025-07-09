package com.example.fallstudie.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users") 
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles;

    private String citizenship; 

    
    public User() {
    }

    public User(String username, String password, List<String> roles, String citizenship) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.citizenship = citizenship;
    }

    public User(String username, String password, String citizenship) {
        this.username = username;
        this.password = password;
        this.roles = Collections.singletonList("USER");
        this.citizenship = citizenship;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    
    public boolean hasRole(String role) {
        return roles != null && roles.contains(role);
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", roles=" + roles +
               ", citizenship='" + citizenship + '\'' +
               '}';
    }
}
