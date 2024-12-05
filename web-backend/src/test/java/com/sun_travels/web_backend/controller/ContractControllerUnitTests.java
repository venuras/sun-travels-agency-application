package com.sun_travels.web_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun_travels.web_backend.dto.ContractDto;
import com.sun_travels.web_backend.service.ContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContractController.class)
class ContractControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractService contractService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testGetAllContracts() throws Exception {
        // Arrange
        ContractDto contract1 = new ContractDto(1L, "Hotel Sunshine", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), List.of());
        ContractDto contract2 = new ContractDto(2L, "Hotel Paradise", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), List.of());
        List<ContractDto> contracts = List.of(contract1, contract2);

        when(contractService.getAllContracts()).thenReturn(contracts);

        // Act & Assert
        mockMvc.perform(get("/api/v1/contract")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].hotelName").value("Hotel Sunshine"))
                .andExpect(jsonPath("$[1].hotelName").value("Hotel Paradise"));

        verify(contractService, times(1)).getAllContracts();
    }

    @Test
    void testAddContract() throws Exception {
        // Arrange
        ContractDto contractDto = new ContractDto(1L, "Hotel Sunshine", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), List.of());
        when(contractService.addContract(any(ContractDto.class))).thenReturn(contractDto);

        // Act & Assert
        mockMvc.perform(post("/api/v1/contract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contractDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.hotelName").value("Hotel Sunshine"))
                .andExpect(jsonPath("$.contractId").value(1));

        verify(contractService, times(1)).addContract(any(ContractDto.class));
    }

    @Test
    void testDeleteContract() throws Exception {
        // Arrange
        Long contractId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/v1/contract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contractId)))
                .andExpect(status().isOk());

        verify(contractService, times(1)).deleteContract(contractId);
    }
}
