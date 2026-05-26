package com.autoconnect.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String passengerName;
    private String passengerPhone;
    private String pickup;
    private String destination;
    private String vehicleType;
    private String status; // REQUESTED, ACCEPTED, COMPLETED, CANCELLED
    private LocalDateTime requestedAt;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
}