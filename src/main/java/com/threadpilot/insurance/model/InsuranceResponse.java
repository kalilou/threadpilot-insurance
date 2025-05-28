package com.threadpilot.insurance.model;

import java.util.List;

public class InsuranceResponse {
    private String personalId;
    private List<Insurance> insurances;
    private List<VehicleInfo> vehicles;
    private String source;

    public InsuranceResponse() {
    }

    public InsuranceResponse(String personalId, List<Insurance> insurances) {
        this.personalId = personalId;
        this.insurances = insurances;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public List<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(List<Insurance> insurances) {
        this.insurances = insurances;
    }

    public List<VehicleInfo> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleInfo> vehicles) {
        this.vehicles = vehicles;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}