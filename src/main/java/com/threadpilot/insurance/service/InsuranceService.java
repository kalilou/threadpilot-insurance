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
import com.threadpilot.insurance.config.Features;
import org.togglz.core.manager.FeatureManager;
import com.threadpilot.insurance.repository.CustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Value;
import com.threadpilot.insurance.repository.PromotionRepository;
import com.threadpilot.insurance.model.Promotion;

@Service
public class InsuranceService {
    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FeatureManager featureManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PromotionRepository promotionRepository;

    public InsuranceResponse getInsurancesByPersonalId(String personalId) {
        List<Insurance> insurances = insuranceRepository.findByInsuranceOwnerNumber(personalId);
        InsuranceResponse insuranceResponse = new InsuranceResponse(personalId, insurances);

        // Apply discounts based on feature toggles
        if (featureManager.isActive(Features.STOCKHOLM_INSURANCE_DISCOUNT)) {
            for (Insurance insurance : insurances) {
                customerRepository.findByPersonIdentificationNumber(insurance.getInsuranceOwnerNumber())
                        .ifPresent(customer -> {
                            if ("Stockholm".equalsIgnoreCase(customer.getCity())) {
                                double originalPrice = insurance.getInsurancePrice();
                                insurance.setDiscount(originalPrice * 0.05);
                                insurance.setInsurancePrice(originalPrice - insurance.getDiscount());
                                // Append Stockholm promotion info
                                promotionRepository
                                        .findFirstByDescriptionOrderByValidityDesc("Stockholm City Promotion")
                                        .ifPresent(promo -> {
                                            String promoInfo = String.format("%s - %d%% off, valid until %s",
                                                    promo.getDescription(), promo.getDiscount(), promo.getValidity());
                                            insuranceResponse.setPromotion(promoInfo);
                                        });
                            }
                        });
            }
        } else if (featureManager.isActive(Features.GOTEBORG_INSURANCE_DISCOUNT)) {
            for (Insurance insurance : insurances) {
                customerRepository.findByPersonIdentificationNumber(insurance.getInsuranceOwnerNumber())
                        .ifPresent(customer -> {
                            if ("Goteborg".equalsIgnoreCase(customer.getCity())
                                    || "Göteborg".equalsIgnoreCase(customer.getCity())) {
                                double originalPrice = insurance.getInsurancePrice();
                                insurance.setDiscount(originalPrice * 0.05);
                                insurance.setInsurancePrice(originalPrice - insurance.getDiscount());
                                // Append Goteborg promotion info
                                promotionRepository.findFirstByDescriptionOrderByValidityDesc("Goteborg City Promotion")
                                        .ifPresent(promo -> {
                                            String promoInfo = String.format("%s - %d%% off, valid until %s",
                                                    promo.getDescription(), promo.getDiscount(), promo.getValidity());
                                            insuranceResponse.setPromotion(promoInfo);
                                        });
                            }
                        });
            }
        } else if (featureManager.isActive(Features.INSURANCE_DISCOUNT)) {
            for (Insurance insurance : insurances) {
                double originalPrice = insurance.getInsurancePrice();
                insurance.setDiscount(originalPrice * 0.10);
                insurance.setInsurancePrice(originalPrice - insurance.getDiscount());
            }
        }

        String url = "http://localhost:8081/api/vehicles/owner/" + personalId;
        ResponseEntity<List<VehicleInfo>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<VehicleInfo>>() {
                });
        List<VehicleInfo> vehicles = response.getBody();

        if (vehicles != null && !vehicles.isEmpty()) {
            insuranceResponse.setVehicles(vehicles);
            insuranceResponse.setSource("@vehicle-service");
        }
        if (featureManager.isActive(Features.BLACK_FRIDAY_PROMOTION)) {
            promotionRepository.findFirstByDescriptionOrderByValidityDesc("Black Friday")
                    .ifPresent(promo -> {
                        String promoInfo = String.format("%s - %d%% off, valid until %s", promo.getDescription(),
                                promo.getDiscount(), promo.getValidity());
                        insuranceResponse.setPromotion(promoInfo);
                    });
        }
        return insuranceResponse;
    }
}