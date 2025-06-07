package com.example.graduationproject.repositrories;

import com.example.graduationproject.model.Address;
import com.example.graduationproject.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findLocationByLongitudeAndLatitude(float latitude, float longitude);
}
