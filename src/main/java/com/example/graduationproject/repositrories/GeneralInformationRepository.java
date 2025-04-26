package com.example.graduationproject.repositrories;

import com.example.graduationproject.model.GeneralInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralInformationRepository extends JpaRepository<GeneralInformation, Long> {
}
