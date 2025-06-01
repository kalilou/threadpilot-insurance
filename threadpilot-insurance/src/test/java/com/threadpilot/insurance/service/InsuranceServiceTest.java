package com.threadpilot.insurance.service;

import com.threadpilot.insurance.model.*;
import com.threadpilot.insurance.repository.*;
import com.threadpilot.insurance.config.Features;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.togglz.core.manager.FeatureManager;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InsuranceServiceTest {
        @Mock
        InsuranceRepository insuranceRepository;
        @Mock
        CustomerRepository customerRepository;
        @Mock
        PromotionRepository promotionRepository;
        @Mock
        FeatureManager featureManager;
        @Mock
        RestTemplate restTemplate;
        @InjectMocks
        InsuranceService insuranceService;

        Insurance insurance;
        Customer customer;

        @SuppressWarnings("unchecked")
        @BeforeEach
        void setup() {
                insurance = new Insurance();
                insurance.setInsurancePrice(100.0);
                insurance.setInsuranceOwnerNumber("199001011001");
                customer = Customer.builder()
                                .personIdentificationNumber("199001011001")
                                .city("Stockholm")
                                .build();
                when(restTemplate.exchange(anyString(), any(), any(),
                                any(org.springframework.core.ParameterizedTypeReference.class)))
                                .thenReturn(org.springframework.http.ResponseEntity.ok(List.of()));
        }

        @Test
        void test10PercentDiscountFeature() {
                when(insuranceRepository.findByInsuranceOwnerNumber(anyString())).thenReturn(List.of(insurance));
                when(featureManager.isActive(Features.INSURANCE_DISCOUNT)).thenReturn(true);
                when(featureManager.isActive(Features.STOCKHOLM_INSURANCE_DISCOUNT)).thenReturn(false);
                when(featureManager.isActive(Features.GOTEBORG_INSURANCE_DISCOUNT)).thenReturn(false);

                InsuranceResponse response = insuranceService.getInsurancesByOwnerNumber("199001011001");
                assertEquals(10.0, response.getInsurances().get(0).getDiscount());
                assertEquals(90.0, response.getInsurances().get(0).getInsurancePrice());
                assertNull(response.getPromotion());
        }

        @Test
        void testStockholmPromotionFeature() {
                Insurance stockholmInsurance = new Insurance();
                stockholmInsurance.setInsurancePrice(100.0);
                stockholmInsurance.setInsuranceOwnerNumber("199001011001");

                Customer stockholmCustomer = Customer.builder()
                                .personIdentificationNumber("199001011001")
                                .city("Stockholm")
                                .build();

                Promotion promo = Promotion.builder()
                                .discount(15)
                                .validity(LocalDate.of(2025, 12, 15))
                                .description("Stockholm City Promotion")
                                .build();

                when(insuranceRepository.findByInsuranceOwnerNumber(anyString()))
                                .thenReturn(List.of(stockholmInsurance));
                when(customerRepository.findByPersonIdentificationNumber(anyString()))
                                .thenReturn(Optional.of(stockholmCustomer));
                when(featureManager.isActive(Features.STOCKHOLM_INSURANCE_DISCOUNT)).thenReturn(true);
                when(featureManager.isActive(Features.INSURANCE_DISCOUNT)).thenReturn(false);
                when(featureManager.isActive(Features.GOTEBORG_INSURANCE_DISCOUNT)).thenReturn(false);
                when(featureManager.isActive(Features.BLACK_FRIDAY_PROMOTION)).thenReturn(false);
                doReturn(Optional.of(promo)).when(promotionRepository)
                                .findFirstByDescriptionOrderByValidityDesc("Stockholm City Promotion");

                InsuranceResponse response = insuranceService.getInsurancesByOwnerNumber("199001011001");
                assertEquals(5.0, response.getInsurances().get(0).getDiscount());
                assertEquals(95.0, response.getInsurances().get(0).getInsurancePrice());
                assertTrue(response.getPromotion().contains("Stockholm City Promotion"));
        }

        @Test
        void testGoteborgPromotionFeature() {
                Insurance goteborgInsurance = new Insurance();
                goteborgInsurance.setInsurancePrice(100.0);
                goteborgInsurance.setInsuranceOwnerNumber("199002022002");
                Customer goteborgCustomer = Customer.builder()
                                .personIdentificationNumber("199002022002")
                                .city("Goteborg")
                                .build();
                Promotion promo = Promotion.builder()
                                .discount(12)
                                .validity(LocalDate.of(2025, 12, 20))
                                .description("Goteborg City Promotion")
                                .build();
                when(insuranceRepository.findByInsuranceOwnerNumber(anyString()))
                                .thenReturn(List.of(goteborgInsurance));
                when(customerRepository.findByPersonIdentificationNumber("199002022002"))
                                .thenReturn(Optional.of(goteborgCustomer));
                when(featureManager.isActive(Features.GOTEBORG_INSURANCE_DISCOUNT)).thenReturn(true);
                when(featureManager.isActive(Features.INSURANCE_DISCOUNT)).thenReturn(false);
                when(featureManager.isActive(Features.STOCKHOLM_INSURANCE_DISCOUNT)).thenReturn(false);
                when(featureManager.isActive(Features.BLACK_FRIDAY_PROMOTION)).thenReturn(false);
                doReturn(Optional.of(promo)).when(promotionRepository)
                                .findFirstByDescriptionOrderByValidityDesc("Goteborg City Promotion");

                InsuranceResponse response = insuranceService.getInsurancesByOwnerNumber("199002022002");
                assertEquals(5.0, response.getInsurances().get(0).getDiscount());
                assertEquals(95.0, response.getInsurances().get(0).getInsurancePrice());
                assertTrue(response.getPromotion().contains("Goteborg City Promotion"));
        }

        @Test
        void testBlackFridayPromotionFeature() {
                Promotion promo = Promotion.builder()
                                .discount(10)
                                .validity(LocalDate.of(2025, 11, 28))
                                .description("Black Friday")
                                .build();
                when(insuranceRepository.findByInsuranceOwnerNumber(anyString())).thenReturn(List.of(insurance));
                when(featureManager.isActive(Features.BLACK_FRIDAY_PROMOTION)).thenReturn(true);
                when(featureManager.isActive(Features.INSURANCE_DISCOUNT)).thenReturn(false);
                when(featureManager.isActive(Features.STOCKHOLM_INSURANCE_DISCOUNT)).thenReturn(false);
                when(featureManager.isActive(Features.GOTEBORG_INSURANCE_DISCOUNT)).thenReturn(false);
                when(promotionRepository.findFirstByDescriptionOrderByValidityDesc("Black Friday"))
                                .thenReturn(Optional.of(promo));

                InsuranceResponse response = insuranceService.getInsurancesByOwnerNumber("199001011001");
                assertTrue(response.getPromotion().contains("Black Friday"));
        }

        @Test
        void testNoDiscountNoPromotion() {
                when(insuranceRepository.findByInsuranceOwnerNumber(anyString())).thenReturn(List.of(insurance));
                when(featureManager.isActive(any())).thenReturn(false);

                InsuranceResponse response = insuranceService.getInsurancesByOwnerNumber("199001011001");
                assertEquals(0.0, response.getInsurances().get(0).getDiscount());
                assertEquals(100.0, response.getInsurances().get(0).getInsurancePrice());
                assertNull(response.getPromotion());
        }
}