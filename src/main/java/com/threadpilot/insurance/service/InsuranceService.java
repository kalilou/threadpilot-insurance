package com.threadpilot.insurance.service;

import com.threadpilot.insurance.model.*;
import com.threadpilot.insurance.repository.InsuranceRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

@Service
public class InsuranceService {
    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private RestTemplate restTemplate;

    public InsuranceResponse getInsurancesByPersonalId(String personalId) {
        List<Insurance> insurances = insuranceRepository.findByInsuranceOwnerNumber(personalId);

        String url = "http://localhost:8081/api/vehicles/owner/" + personalId;
        ResponseEntity<List<VehicleInfo>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<VehicleInfo>>() {
                });
        List<VehicleInfo> vehicles = response.getBody();

        InsuranceResponse insuranceResponse = new InsuranceResponse(personalId, insurances);
        if (vehicles != null && !vehicles.isEmpty()) {
            insuranceResponse.setVehicles(vehicles);
            insuranceResponse.setSource("@vehicle-service");
        }
        return insuranceResponse;
    }
}