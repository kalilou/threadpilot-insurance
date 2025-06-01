package com.threadpilot.insurance.repository;

import com.threadpilot.insurance.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByPersonIdentificationNumber(String personIdentificationNumber);
}