package com.example.fallstudie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AufgabenstellungController {

    @GetMapping("/aufgabenstellung")
    public String zeigeAufgabenstellung() {
        return "aufgabenstellung"; 
    }
}
