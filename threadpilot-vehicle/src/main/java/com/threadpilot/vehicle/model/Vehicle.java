package com.threadpilot.vehicle.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @Column(name = "registration_number")
    private String registrationNumber;
    @Column(name = "fuel_type")
    private String fuelType;
    @Column(name = "model")
    private String model;
    @Column(name = "make")
    private String make;
    @Column(name = "year")
    private int year;
    @Column(name = "color")
    private String color;
    @Column(name = "owner_personal_number")
    private String ownerPersonalNumber;
    @Column(name = "mileage")
    private Long mileage;
}
