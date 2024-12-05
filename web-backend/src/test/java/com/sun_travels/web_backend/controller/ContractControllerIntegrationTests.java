package com.sun_travels.web_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun_travels.web_backend.config.TestContainersConfiguration;
import com.sun_travels.web_backend.dto.ContractDto;
import com.sun_travels.web_backend.dto.ContractDetailDto;
import com.sun_travels.web_backend.repository.ContractRepository;
import com.sun_travels.web_backend.service.ContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class ContractControllerIntegrationTests extends TestContainersConfiguration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ContractService contractService;

    @Autowired
    ContractRepository contractRepository;

    @BeforeEach
    void resetDatabase()
    {
        contractRepository.deleteAll();
    }

    @Test
    void testGetAllContracts() throws Exception {
        // Arrange: Add a contract to the database
        ContractDetailDto roomDto = new ContractDetailDto(null, "Deluxe", 250.0, 5, 2);
        ContractDto contractDto = new ContractDto(null, "Test Hotel", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), List.of(roomDto));
        contractService.addContract(contractDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/contract")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].hotelName", is("Test Hotel")));
    }

    @Test
    void testAddContract() throws Exception {
        // Arrange: Create a new ContractDto
        ContractDetailDto roomDto = new ContractDetailDto(null, "Suite", 300.0, 3, 2);
        ContractDto contractDto = new ContractDto(null, "Luxury Inn", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), List.of(roomDto));
        String jsonRequest = objectMapper.writeValueAsString(contractDto);

        // Act & Assert
        mockMvc.perform(post("/api/v1/contract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.hotelName", is("Luxury Inn")))
                .andExpect(jsonPath("$.contractDetails[0].roomType", is("Suite")));
    }

    @Test
    void testDeleteContract() throws Exception {
        // Arrange: Add a contract to the database
        ContractDetailDto roomDto = new ContractDetailDto(null, "Economy", 100.0, 10, 2);
        ContractDto contractDto = new ContractDto(null, "Budget Stay", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), List.of(roomDto));
        ContractDto savedContract = contractService.addContract(contractDto);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/contract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(savedContract.getContractId().toString()))
                .andExpect(status().isOk());

        // Verify the contract no longer exists
        mockMvc.perform(get("/api/v1/contract")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testAddContractWithInvalidData() throws Exception {
        // Arrange: Create an invalid ContractDto (missing hotelName)
        ContractDetailDto roomDto = new ContractDetailDto(null, "Standard", 150.0, 5, 2);
        ContractDto invalidContractDto = new ContractDto(null, null, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), List.of(roomDto));
        String jsonRequest = objectMapper.writeValueAsString(invalidContractDto);

        // Act & Assert
        mockMvc.perform(post("/api/v1/contract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }
}
