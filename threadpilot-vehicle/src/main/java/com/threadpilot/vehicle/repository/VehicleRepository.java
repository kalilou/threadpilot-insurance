package com.threadpilot.vehicle.repository;

import com.threadpilot.vehicle.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    Vehicle findByRegistrationNumber(String registrationNumber);

    List<Vehicle> findByOwnerPersonalNumber(String insuranceOwnerNumber);
}
