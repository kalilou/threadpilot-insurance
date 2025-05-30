package com.threadpilot.insurance.controller;

import com.threadpilot.insurance.model.InsuranceResponse;
import com.threadpilot.insurance.service.InsuranceService;
import com.threadpilot.insurance.model.Insurance;
import com.threadpilot.insurance.model.Vehicle;
import com.threadpilot.insurance.model.Promotion;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Date;
import java.time.ZoneOffset;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultActions;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(InsuranceController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InsuranceControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private InsuranceService insuranceService;

        @Autowired
        private ObjectMapper objectMapper;

        Insurance insurance;
        InsuranceResponse insuranceResponse;
        Vehicle vehicle;
        Promotion promotion;

        @BeforeEach
        void setup() {

                insurance = Insurance.builder()
                                .insuranceNumber("1234567890")
                                .insuranceType("PetInsurance")
                                .insurancePrice(10)
                                .discount(0)
                                .insuranceStartDate(Date.from(
                                                LocalDate.parse("2025-01-01").atStartOfDay().toInstant(ZoneOffset.UTC)))
                                .insuranceEndDate(Date.from(
                                                LocalDate.parse("2025-12-31").atStartOfDay().toInstant(ZoneOffset.UTC)))
                                .insuranceStatus("Active")
                                .insuranceOwnerNumber("199001011235")
                                .build();

                insuranceResponse = InsuranceResponse.builder()
                                .insurances(List.of(insurance))
                                .insuranceOwnerNumber(insurance.getInsuranceOwnerNumber())
                                .build();

                vehicle = Vehicle.builder()
                                .registrationNumber("1234567890")
                                .model("Car")
                                .make("Toyota")
                                .year(2020)
                                .color("Red")
                                .ownerPersonalNumber("199001011235")
                                .mileage(1000L)
                                .build();
        }

        // Get Controller Test

        @Order(1)
        @Test
        void testGetInsurances() throws Exception {
                String insuranceOwnerNumber = "199001011235";
                given(insuranceService.getInsurancesByOwnerNumber(insuranceOwnerNumber)).willReturn(insuranceResponse);

                ResultActions response = mockMvc
                                .perform(get("/api/insurances/{insuranceOwnerNumber}", insuranceOwnerNumber)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(insuranceResponse)));

                response.andDo(print()).andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].insuranceOwnerNumber").value(insuranceOwnerNumber))
                                .andExpect(jsonPath("$[0].insurances[0].insuranceNumber")
                                                .value(insurance.getInsuranceNumber()))
                                .andExpect(jsonPath("$[0].insurances[0].insuranceType")
                                                .value(insurance.getInsuranceType()))
                                .andExpect(jsonPath("$[0].insurances[0].insurancePrice")
                                                .value(insurance.getInsurancePrice()))
                                .andExpect(jsonPath("$[0].insurances[0].discount").value(insurance.getDiscount()))
                                .andExpect(jsonPath("$[0].insurances[0].insuranceStartDate")
                                                .value("2025-01-01T00:00:00.000+00:00"))
                                .andExpect(jsonPath("$[0].insurances[0].insuranceEndDate")
                                                .value("2025-12-31T00:00:00.000+00:00"))
                                .andExpect(jsonPath("$[0].insurances[0].insuranceStatus")
                                                .value(insurance.getInsuranceStatus()));
        }

        @Order(2)
        @Test
        void testGetInsuranceWithVehicleInfo() throws Exception {
                String insuranceOwnerNumber = "199001011235";

                insuranceResponse = InsuranceResponse.builder()
                                .insurances(List.of(insurance))
                                .insuranceOwnerNumber(insurance.getInsuranceOwnerNumber())
                                .vehicles(List.of(vehicle))
                                .build();

                given(insuranceService.getInsurancesByOwnerNumber(insuranceOwnerNumber)).willReturn(insuranceResponse);

                ResultActions response = mockMvc
                                .perform(get("/api/insurances/{insuranceOwnerNumber}", insuranceOwnerNumber)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(insuranceResponse)));

                response.andDo(print()).andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].insuranceOwnerNumber").value(insuranceOwnerNumber))
                                .andExpect(jsonPath("$[0].insurances[0].insuranceNumber")
                                                .value(insurance.getInsuranceNumber()))
                                .andExpect(jsonPath("$[0].insurances[0].insuranceType")
                                                .value(insurance.getInsuranceType()))
                                .andExpect(jsonPath("$[0].insurances[0].insurancePrice")
                                                .value(insurance.getInsurancePrice()))
                                .andExpect(jsonPath("$[0].insurances[0].discount").value(insurance.getDiscount()))
                                .andExpect(jsonPath("$[0].insurances[0].insuranceStartDate")
                                                .value("2025-01-01T00:00:00.000+00:00"))
                                .andExpect(jsonPath("$[0].insurances[0].insuranceEndDate")
                                                .value("2025-12-31T00:00:00.000+00:00"))
                                .andExpect(jsonPath("$[0].insurances[0].insuranceStatus")
                                                .value(insurance.getInsuranceStatus()))
                                .andExpect(jsonPath("$[0].vehicles[0].registrationNumber")
                                                .value(vehicle.getRegistrationNumber()))
                                .andExpect(jsonPath("$[0].vehicles[0].model").value(vehicle.getModel()))
                                .andExpect(jsonPath("$[0].vehicles[0].make").value(vehicle.getMake()))
                                .andExpect(jsonPath("$[0].vehicles[0].year").value(vehicle.getYear()))
                                .andExpect(jsonPath("$[0].vehicles[0].color").value(vehicle.getColor()))
                                .andExpect(jsonPath("$[0].vehicles[0].ownerPersonalNumber")
                                                .value(vehicle.getOwnerPersonalNumber()))
                                .andExpect(jsonPath("$[0].vehicles[0].mileage").value(vehicle.getMileage()));
        }

        @Order(3)
        @Test
        void testGetInsuranceWithPromotionBlackFriday() throws Exception {
                String insuranceOwnerNumber = "199001011235";
                promotion = Promotion.builder()
                                .description("Black Friday")
                                .discount(10)
                                .validity(LocalDate.parse("2025-12-31"))
                                .build();

                insuranceResponse = InsuranceResponse.builder()
                                .insurances(List.of(insurance))
                                .insuranceOwnerNumber(insurance.getInsuranceOwnerNumber())
                                .promotion("Black Friday - 10% off, valid until 2025-12-31")
                                .build();

                given(insuranceService.getInsurancesByOwnerNumber(insuranceOwnerNumber)).willReturn(insuranceResponse);

                ResultActions response = mockMvc
                                .perform(get("/api/insurances/{insuranceOwnerNumber}", insuranceOwnerNumber)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(insuranceResponse)));

                response.andDo(print()).andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].insuranceOwnerNumber").value(insuranceOwnerNumber))
                                .andExpect(jsonPath("$[0].insurances[0].insuranceNumber")
                                                .value(insurance.getInsuranceNumber()))
                                .andExpect(jsonPath("$[0].insurances[0].insuranceStatus")
                                                .value(insurance.getInsuranceStatus()))
                                .andExpect(jsonPath("$[0].promotion")
                                                .value("Black Friday - 10% off, valid until 2025-12-31"));
        }
}