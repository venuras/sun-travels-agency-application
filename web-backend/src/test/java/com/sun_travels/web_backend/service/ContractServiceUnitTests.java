package com.sun_travels.web_backend.service;

import com.sun_travels.web_backend.dto.ContractDto;
import com.sun_travels.web_backend.exception.ContractNotFoundException;
import com.sun_travels.web_backend.model.Contract;
import com.sun_travels.web_backend.repository.ContractRepository;
import com.sun_travels.web_backend.utils.AppUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContractServiceUnitTests {

    @Mock
    private ContractRepository contractRepository;

    @InjectMocks
    private ContractService contractService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddContract() {
        // Arrange
        ContractDto contractDto = new ContractDto(1L, "Hotel Sunshine", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), List.of());
        Contract contract = AppUtils.ContractMapper.dtoToEntity(contractDto);
        when(contractRepository.save(any(Contract.class))).thenReturn(contract);

        // Act
        ContractDto savedContract = contractService.addContract(contractDto);

        // Assert
        assertNotNull(savedContract);
        assertEquals(contractDto.getHotelName(), savedContract.getHotelName());
        verify(contractRepository, times(1)).save(any(Contract.class));
    }

    @Test
    void testDeleteContractWhenExists() {
        // Arrange
        Long contractId = 1L;
        Contract contract = new Contract();
        contract.setContractId(contractId);
        when(contractRepository.findById(contractId)).thenReturn(Optional.of(contract));

        // Act
        assertDoesNotThrow(() -> contractService.deleteContract(contractId));

        // Assert
        verify(contractRepository, times(1)).findById(contractId);
        verify(contractRepository, times(1)).deleteById(contractId);
    }

    @Test
    void testDeleteContractWhenNotFound() {
        // Arrange
        Long contractId = 1L;
        when(contractRepository.findById(contractId)).thenReturn(Optional.empty());

        // Act & Assert
        ContractNotFoundException exception = assertThrows(ContractNotFoundException.class, () -> contractService.deleteContract(contractId));
        assertEquals("Unable to find contract with id 1", exception.getMessage());
        verify(contractRepository, times(1)).findById(contractId);
        verify(contractRepository, times(0)).deleteById(contractId);
    }

    @Test
    void testGetAllContracts() {
        // Arrange
        Contract contract1 = new Contract();
        contract1.setContractId(1L);
        contract1.setHotelName("Hotel Sunshine");
        contract1.setContractValidFrom(LocalDate.of(2023, 1, 1));
        contract1.setContractValidTill(LocalDate.of(2023, 12, 31));
        contract1.setContractDetails(new ArrayList<>());

        Contract contract2 = new Contract();
        contract2.setContractId(2L);
        contract2.setHotelName("Hotel Paradise");
        contract2.setContractValidFrom(LocalDate.of(2024, 1, 1));
        contract2.setContractValidTill(LocalDate.of(2024, 12, 31));
        contract2.setContractDetails(new ArrayList<>());

        when(contractRepository.findAll()).thenReturn(List.of(contract1, contract2));

        // Act
        List<ContractDto> contractDtos = contractService.getAllContracts();

        // Assert
        assertNotNull(contractDtos);
        assertEquals(2, contractDtos.size());
        assertEquals("Hotel Sunshine", contractDtos.get(0).getHotelName());
        assertEquals("Hotel Paradise", contractDtos.get(1).getHotelName());
        verify(contractRepository, times(1)).findAll();
    }
}
