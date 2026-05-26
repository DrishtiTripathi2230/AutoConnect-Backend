package com.autoconnect.backend.controller;

import com.autoconnect.backend.model.Ride;
import com.autoconnect.backend.model.Driver;
import com.autoconnect.backend.repository.RideRepository;
import com.autoconnect.backend.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rides")
@CrossOrigin(origins = "*")
public class RideController {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private DriverRepository driverRepository;

    // Passenger requests a ride
    @PostMapping("/request")
    public Ride requestRide(@RequestBody Ride ride) {
        ride.setStatus("REQUESTED");
        ride.setRequestedAt(LocalDateTime.now());
        return rideRepository.save(ride);
    }

    // Get all requested rides (drivers see this)
    @GetMapping("/requested")
    public List<Ride> getRequestedRides() {
        return rideRepository.findByStatus("REQUESTED");
    }

    // Driver accepts a ride
    @PostMapping("/{rideId}/accept/{driverId}")
    public Ride acceptRide(@PathVariable Long rideId, @PathVariable Long driverId) {
        Ride ride = rideRepository.findById(rideId)
            .orElseThrow(() -> new RuntimeException("Ride not found"));
        Driver driver = driverRepository.findById(driverId)
            .orElseThrow(() -> new RuntimeException("Driver not found"));

        ride.setStatus("ACCEPTED");
        ride.setDriver(driver);
        driver.setAvailable(false);
        driverRepository.save(driver);
        return rideRepository.save(ride);
    }

    // Complete a ride
    @PostMapping("/{rideId}/complete")
    public Ride completeRide(@PathVariable Long rideId) {
        Ride ride = rideRepository.findById(rideId)
            .orElseThrow(() -> new RuntimeException("Ride not found"));

        ride.setStatus("COMPLETED");
        if (ride.getDriver() != null) {
            Driver driver = ride.getDriver();
            driver.setAvailable(true);
            driverRepository.save(driver);
        }
        return rideRepository.save(ride);
    }

    // Get ride status
    @GetMapping("/{rideId}/status")
    public String getRideStatus(@PathVariable Long rideId) {
        Ride ride = rideRepository.findById(rideId)
            .orElseThrow(() -> new RuntimeException("Ride not found"));
        return "{\"status\":\"" + ride.getStatus() + "\"}";
    }
}