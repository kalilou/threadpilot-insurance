package com.threadpilot.insurance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.util.Date;

@Entity
@Table(name = "insurance")
public class Insurance {
    @Id
    @Column(name = "insurance_number")
    private String insuranceNumber;
    @Column(name = "insurance_type")
    private String insuranceType;
    @Column(name = "insurance_price")
    private double insurancePrice;
    @Column(name = "discount")
    private double discount;
    @Column(name = "insurance_start_date")
    private Date insuranceStartDate;
    @Column(name = "insurance_end_date")
    private Date insuranceEndDate;
    @Column(name = "insurance_status")
    private String insuranceStatus;
    @Column(name = "insurance_owner_number")
    private String insuranceOwnerNumber;

    public Insurance() {
    }

    public Insurance(String insuranceNumber, String insuranceType, double insurancePrice, double discount,
            Date insuranceStartDate, Date insuranceEndDate, String insuranceStatus, String insuranceOwnerNumber) {
        this.insuranceNumber = insuranceNumber;
        this.insuranceType = insuranceType;
        this.insurancePrice = insurancePrice;
        this.discount = discount;
        this.insuranceStartDate = insuranceStartDate;
        this.insuranceEndDate = insuranceEndDate;
        this.insuranceStatus = insuranceStatus;
        this.insuranceOwnerNumber = insuranceOwnerNumber;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public double getInsurancePrice() {
        return insurancePrice;
    }

    public void setInsurancePrice(double insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getInsuranceStartDate() {
        return insuranceStartDate;
    }

    public void setInsuranceStartDate(Date insuranceStartDate) {
        this.insuranceStartDate = insuranceStartDate;
    }

    public Date getInsuranceEndDate() {
        return insuranceEndDate;
    }

    public void setInsuranceEndDate(Date insuranceEndDate) {
        this.insuranceEndDate = insuranceEndDate;
    }
}