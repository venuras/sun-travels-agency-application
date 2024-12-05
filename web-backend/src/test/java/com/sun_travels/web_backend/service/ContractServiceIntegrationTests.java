package com.sun_travels.web_backend.service;

import com.sun_travels.web_backend.config.TestContainersConfiguration;
import com.sun_travels.web_backend.dto.ContractDto;
import com.sun_travels.web_backend.dto.ContractDetailDto;
import com.sun_travels.web_backend.exception.ContractNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class ContractServiceIntegrationTests extends TestContainersConfiguration {

    @Autowired
    private ContractService contractService;

    @Test
    void testAddContract() {
        // Arrange
        ContractDetailDto roomDto = new ContractDetailDto(null, "Deluxe", 200.0, 5, 2);
        ContractDto contractDto = new ContractDto(null, "Luxury Inn", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), List.of(roomDto));

        // Act
        ContractDto savedContract = contractService.addContract(contractDto);

        // Assert
        assertNotNull(savedContract.getContractId());
        assertEquals("Luxury Inn", savedContract.getHotelName());
        assertEquals(1, savedContract.getContractDetails().size());
        assertEquals("Deluxe", savedContract.getContractDetails().get(0).getRoomType());
    }

    @Test
    void testDeleteContract() {
        // Arrange
        ContractDetailDto roomDto = new ContractDetailDto(null, "Standard", 150.0, 3, 2);
        ContractDto contractDto = new ContractDto(null, "Standard Stay", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), List.of(roomDto));
        ContractDto savedContract = contractService.addContract(contractDto);

        // Act & Assert
        contractService.deleteContract(savedContract.getContractId());
        assertThrows(ContractNotFoundException.class, () -> contractService.deleteContract(savedContract.getContractId()));
    }

    @Test
    void testGetAllContracts() {
        // Arrange
        ContractDetailDto roomDto1 = new ContractDetailDto(null, "Suite", 300.0, 2, 4);
        ContractDetailDto roomDto2 = new ContractDetailDto(null, "Economy", 100.0, 10, 2);

        ContractDto contractDto1 = new ContractDto(null, "Grand Hotel", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), List.of(roomDto1));
        ContractDto contractDto2 = new ContractDto(null, "Budget Inn", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), List.of(roomDto2));

        contractService.addContract(contractDto1);
        contractService.addContract(contractDto2);

        // Act
        List<ContractDto> contracts = contractService.getAllContracts();

        // Assert
        assertEquals(2, contracts.size());
        assertTrue(contracts.stream().anyMatch(contract -> contract.getHotelName().equals("Grand Hotel")));
        assertTrue(contracts.stream().anyMatch(contract -> contract.getHotelName().equals("Budget Inn")));
    }

    @Test
    void testAddContractWithMultipleRooms() {
        // Arrange
        ContractDetailDto roomDto1 = new ContractDetailDto(null, "Suite", 350.0, 2, 3);
        ContractDetailDto roomDto2 = new ContractDetailDto(null, "Deluxe", 250.0, 5, 2);

        ContractDto contractDto = new ContractDto(null, "Multi-Rooms Hotel", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), List.of(roomDto1, roomDto2));

        // Act
        ContractDto savedContract = contractService.addContract(contractDto);

        // Assert
        assertNotNull(savedContract.getContractId());
        assertEquals(2, savedContract.getContractDetails().size());
        assertTrue(savedContract.getContractDetails().stream().anyMatch(room -> room.getRoomType().equals("Suite")));
        assertTrue(savedContract.getContractDetails().stream().anyMatch(room -> room.getRoomType().equals("Deluxe")));
    }
}
