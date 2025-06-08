package com.example.graduationproject.controllers;

import com.example.graduationproject.model.GeneralInformation;
import com.example.graduationproject.services.GeneralInformationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/general-information")
public class GeneralInformationController {
    private final GeneralInformationService generalInformationService;

    public GeneralInformationController(GeneralInformationService generalInformationService) {
        this.generalInformationService = generalInformationService;
    }

    @GetMapping("")
    ResponseEntity<List<GeneralInformation>> getGeneralInformation(){
        return ResponseEntity.ok(generalInformationService.getAllGeneralInformation());
    }
    @PostMapping("/create-all")
    ResponseEntity<GeneralInformation[]> createGeneralInformation(@Valid @RequestBody GeneralInformation[] generalInformation){
        for (GeneralInformation info : generalInformation) {
            generalInformationService.saveGeneralInformation(info);
        }
        return ResponseEntity.ok(generalInformation);
    }
}
