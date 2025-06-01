package com.threadpilot.insurance.contract;

import com.threadpilot.insurance.repository.PromotionRepository;
import com.threadpilot.insurance.repository.CustomerRepository;
import com.threadpilot.insurance.repository.InsuranceRepository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.web.client.RestTemplate;
import com.threadpilot.insurance.model.Vehicle;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = "com.threadpilot:vehicle-service:0.0.1-SNAPSHOT:stubs:8081", stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@ActiveProfiles("test")
public class ContractVehicleTest {

    private final RestTemplate restTemplate = new RestTemplate();

    @MockBean
    private InsuranceRepository insuranceRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private PromotionRepository promotionRepository;

    @Test
    @DisplayName("Contract test for getting vehicle by owner personal number")
    void shouldGetVehicleByRegistrationNumber() {
        String url = "http://localhost:8081/api/vehicles/owner/199001011235";
        Vehicle[] vehicles = restTemplate.getForObject(url, Vehicle[].class);

        assertThat(vehicles).isNotNull();
        assertThat(vehicles.length).isGreaterThan(0);
        Vehicle vehicle = vehicles[0];
        assertThat(vehicle.getRegistrationNumber()).isEqualTo("1234567890");
        assertThat(vehicle.getOwnerPersonalNumber()).isEqualTo("199001011235");
        assertThat(vehicle.getMake()).isEqualTo("Toyota");
        assertThat(vehicle.getModel()).isEqualTo("Corolla");
        assertThat(vehicle.getYear()).isEqualTo(2020);
        assertThat(vehicle.getColor()).isEqualTo("Red");
        assertThat(vehicle.getFuelType()).isEqualTo("Petrol");
        assertThat(vehicle.getMileage()).isEqualTo(1000);

    }
}
