package com.threadpilot.insurance.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@Builder
public class InsuranceResponse {
    private String insuranceOwnerNumber;
    private List<Insurance> insurances;
    private List<Vehicle> vehicles;
    private String source;
    private String promotion;

    public InsuranceResponse() {
    }

    public InsuranceResponse(String insuranceOwnerNumber, List<Insurance> insurances) {
        this.insuranceOwnerNumber = insuranceOwnerNumber;
        this.insurances = insurances;
    }
}