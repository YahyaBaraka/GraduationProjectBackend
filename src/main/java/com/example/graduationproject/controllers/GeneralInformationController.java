package com.example.graduationproject.controllers;

import com.example.graduationproject.model.GeneralInformation;
import com.example.graduationproject.services.GeneralInformationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/general-information")
@Slf4j
public class GeneralInformationController {
    private final GeneralInformationService generalInformationService;

    public GeneralInformationController(GeneralInformationService generalInformationService) {
        this.generalInformationService = generalInformationService;
    }

    @GetMapping("")
    ResponseEntity<List<GeneralInformation>> getGeneralInformation(){
        log.info("Received Request to get all general information records");
        return ResponseEntity.ok(generalInformationService.getAllGeneralInformation());
    }
    @PostMapping("/create-all")
    ResponseEntity<GeneralInformation[]> createGeneralInformation(@Valid @RequestBody GeneralInformation[] generalInformation){
        log.info("Received Request to create general information with values: " + Arrays.toString(generalInformation));
        for (GeneralInformation info : generalInformation) {
            generalInformationService.saveGeneralInformation(info);
        }
        return ResponseEntity.ok(generalInformation);
    }
}
