package com.threadpilot.insurance.controller;

import com.threadpilot.insurance.model.InsuranceResponse;
import com.threadpilot.insurance.service.InsuranceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/insurances")
public class InsuranceController {
    private final InsuranceService insuranceService;

    @Autowired
    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    /**
     * Get insurance information for a given owner number.
     * 
     * @param insuranceOwnerNumber the owner's personal identification number
     * @return InsuranceResponse for the owner
     */
    @GetMapping("/{insuranceOwnerNumber}")
    public InsuranceResponse getInsurances(@PathVariable String insuranceOwnerNumber) {
        return insuranceService.getInsurancesByOwnerNumber(insuranceOwnerNumber);
    }
}
