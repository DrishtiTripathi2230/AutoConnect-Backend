package com.autoconnect.backend.controller;

import com.autoconnect.backend.model.Driver;
import com.autoconnect.backend.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@CrossOrigin(origins = "*")
public class DriverController {

    @Autowired
    private DriverRepository driverRepository;

    // Register a new driver
    @PostMapping("/register")
    public Driver registerDriver(@RequestBody Driver driver) {
        driver.setAvailable(true);
        driver.setRating(5.0);
        return driverRepository.save(driver);
    }

    // Get all available drivers
    @GetMapping("/available")
    public List<Driver> getAvailableDrivers() {
        return driverRepository.findByAvailableTrue();
    }

    // Get drivers by vehicle type
    @GetMapping("/type/{vehicleType}")
    public List<Driver> getDriversByType(@PathVariable String vehicleType) {
        return driverRepository.findByVehicleType(vehicleType);
    }

    // Get all drivers
    @GetMapping("/all")
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }
}