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

    // public String getInsuranceOwnerNumber() {
    // return insuranceOwnerNumber;
    // }

    // public void setInsuranceOwnerNumber(String insuranceOwnerNumber) {
    // this.insuranceOwnerNumber = insuranceOwnerNumber;
    // }

    // public List<Insurance> getInsurances() {
    // return insurances;
    // }

    // public void setInsurances(List<Insurance> insurances) {
    // this.insurances = insurances;
    // }

    // public List<Vehicle> getVehicles() {
    // return vehicles;
    // }

    // public void setVehicles(List<Vehicle> vehicles) {
    // this.vehicles = vehicles;
    // }

    // public String getSource() {
    // return source;
    // }

    // public void setSource(String source) {
    // this.source = source;
    // }

    // public String getPromotion() {
    // return promotion;
    // }

    // public void setPromotion(String promotion) {
    // this.promotion = promotion;
    // }
}