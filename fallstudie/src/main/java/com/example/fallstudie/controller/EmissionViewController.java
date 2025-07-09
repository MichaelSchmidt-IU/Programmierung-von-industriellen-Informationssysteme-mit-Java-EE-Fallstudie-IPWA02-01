package com.example.fallstudie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmissionViewController {

    @GetMapping("/emissions/view")
    public String showEmissionView() {
        return "emissions-view"; 
    }

    @GetMapping("/emissions/upload")
    public String showUploadForm() {
        return "emissions-upload"; 
    }

}
