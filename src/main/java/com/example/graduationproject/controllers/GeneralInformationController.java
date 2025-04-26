package com.example.graduationproject.controllers;

import com.example.graduationproject.model.GeneralInformation;
import com.example.graduationproject.services.GeneralInformationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/general-information")
public class GeneralInformationController {
    private final GeneralInformationService generalInformationService;

    public GeneralInformationController(GeneralInformationService generalInformationService) {
        this.generalInformationService = generalInformationService;
    }

    @GetMapping("")
    ResponseEntity<List<GeneralInformation>> getRecipes(){
        return ResponseEntity.ok(generalInformationService.getAllGeneralInformation());
    }
}
