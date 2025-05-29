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
    @Autowired
    private InsuranceService insuranceService;

    @GetMapping("/{insuranceOwnerNumber}")
    public List<InsuranceResponse> getInsurances(@PathVariable String insuranceOwnerNumber) {
        return List.of(insuranceService.getInsurancesByOwnerNumber(insuranceOwnerNumber));
    }
}
