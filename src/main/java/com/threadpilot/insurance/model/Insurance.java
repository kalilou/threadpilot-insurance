package com.threadpilot.insurance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "insurance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}