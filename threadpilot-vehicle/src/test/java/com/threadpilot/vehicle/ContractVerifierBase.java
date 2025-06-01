package com.threadpilot.vehicle;

import com.threadpilot.vehicle.controller.VehicleController;
import com.threadpilot.vehicle.model.Vehicle;
import com.threadpilot.vehicle.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest(classes = VehicleServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, LiquibaseAutoConfiguration.class })
@AutoConfigureMockMvc
public abstract class ContractVerifierBase {

    @Autowired
    VehicleController vehicleController;

    @MockBean
    VehicleService vehicleService;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(vehicleController);
        Vehicle vehicle = new Vehicle(
                "1234567890", // registrationNumber
                "Petrol", // fuelType
                "Corolla", // model
                "Toyota", // make
                2020, // year
                "Red", // color
                "199001011235", // ownerPersonalNumber
                1000L // mileage
        );
        Mockito.when(vehicleService.getVehicleByRegistration("1234567890"))
                .thenReturn(vehicle);
        Mockito.when(vehicleService.findByOwnerPersonalNumber("199001011235"))
                .thenReturn(java.util.List.of(vehicle));
    }
}