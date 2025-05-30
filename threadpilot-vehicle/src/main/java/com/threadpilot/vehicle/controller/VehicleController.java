package com.threadpilot.vehicle.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.threadpilot.vehicle.model.Vehicle;
import com.threadpilot.vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/{registrationNumber}")
    public Vehicle getVehicleByRegistration(@PathVariable String registrationNumber) {
        return vehicleService.getVehicleByRegistration(registrationNumber);
    }

    @GetMapping("/owner/{personalId}")
    public List<Vehicle> getVehiclesByOwner(@PathVariable String personalId) {
        return vehicleService.findByOwnerPersonalNumber(personalId);
    }
}