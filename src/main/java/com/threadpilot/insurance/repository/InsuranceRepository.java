package com.threadpilot.insurance.repository;

import com.threadpilot.insurance.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    List<Insurance> findByInsuranceOwnerNumber(String insuranceOwnerNumber);
}