package com.example.graduationproject.controllers;

import com.example.graduationproject.model.Location;
import com.example.graduationproject.services.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api/map")
@Slf4j
public class LocationController {
    private final LocationService locationService;
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }
    @GetMapping("/all")
    ResponseEntity<List<Location>> getAllLocations() {
        log.info("Received Request to get all location records");
        return ResponseEntity.ok(locationService.getAllLocations());
    }
}
