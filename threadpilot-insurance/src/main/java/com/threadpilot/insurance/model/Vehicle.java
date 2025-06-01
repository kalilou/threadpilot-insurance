package com.threadpilot.insurance.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String registrationNumber;
    private String fuelType;
    private String model;
    private String make;
    private int year;
    private String color;
    private String ownerPersonalNumber;
    private Long mileage;
}
