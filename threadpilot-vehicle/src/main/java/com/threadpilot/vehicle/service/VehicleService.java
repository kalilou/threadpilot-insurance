package com.threadpilot.vehicle.service;

import com.threadpilot.vehicle.model.Vehicle;
import com.threadpilot.vehicle.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle getVehicleByRegistration(String registrationNumber) {
        return vehicleRepository.findByRegistrationNumber(registrationNumber);
    }

    public List<Vehicle> findByOwnerPersonalNumber(String personalId) {
        return vehicleRepository.findByOwnerPersonalNumber(personalId);
    }
}