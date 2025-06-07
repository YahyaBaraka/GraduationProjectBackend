package com.example.graduationproject.services;

import com.example.graduationproject.model.GeneralInformation;
import com.example.graduationproject.repositrories.GeneralInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneralInformationService {
    private final GeneralInformationRepository generalInformationRepository;

    public GeneralInformationService(GeneralInformationRepository generalInformationRepository) {
        this.generalInformationRepository = generalInformationRepository;
    }

    public List<GeneralInformation> getAllGeneralInformation(){
        return generalInformationRepository.findAll();
    }
}
