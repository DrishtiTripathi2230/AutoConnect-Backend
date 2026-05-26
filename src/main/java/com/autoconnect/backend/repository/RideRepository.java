package com.autoconnect.backend.repository;

import com.autoconnect.backend.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByStatus(String status);
    List<Ride> findByPassengerPhone(String phone);
}