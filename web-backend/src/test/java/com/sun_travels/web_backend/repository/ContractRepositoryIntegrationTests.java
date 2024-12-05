package com.sun_travels.web_backend.repository;

import com.sun_travels.web_backend.config.TestContainersConfiguration;
import com.sun_travels.web_backend.model.Contract;
import com.sun_travels.web_backend.model.ContractDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class ContractRepositoryIntegrationTests extends TestContainersConfiguration {

    @Autowired
    private ContractRepository contractRepository;

    @Test
    void testSaveContract() {
        // Arrange
        Contract contract = new Contract();
        contract.setHotelName("Test Hotel");
        contract.setContractValidFrom(LocalDate.of(2023, 1, 1));
        contract.setContractValidTill(LocalDate.of(2023, 12, 31));

        ContractDetail room = new ContractDetail();
        room.setRoomType("Standard");
        room.setRoomPrice(100.0);
        room.setRoomCount(5);
        room.setMaxAdultCount(2);

        contract.setContractDetails(List.of(room));

        // Act
        Contract savedContract = contractRepository.save(contract);

        // Assert
        assertNotNull(savedContract.getContractId());
        assertEquals("Test Hotel", savedContract.getHotelName());
        assertEquals(1, savedContract.getContractDetails().size());
        assertEquals("Standard", savedContract.getContractDetails().getFirst().getRoomType());
    }

    @Test
    void testFindById() {
        // Arrange
        Contract contract = new Contract();
        contract.setHotelName("Test Hotel");
        contract.setContractValidFrom(LocalDate.of(2023, 1, 1));
        contract.setContractValidTill(LocalDate.of(2023, 12, 31));

        Contract savedContract = contractRepository.save(contract);

        // Act
        Optional<Contract> retrievedContract = contractRepository.findById(savedContract.getContractId());

        // Assert
        assertTrue(retrievedContract.isPresent());
        assertEquals("Test Hotel", retrievedContract.get().getHotelName());
    }

    @Test
    void testDeleteContract() {
        // Arrange
        Contract contract = new Contract();
        contract.setHotelName("Test Hotel");
        contract.setContractValidFrom(LocalDate.of(2023, 1, 1));
        contract.setContractValidTill(LocalDate.of(2023, 12, 31));

        Contract savedContract = contractRepository.save(contract);

        // Act
        contractRepository.deleteById(savedContract.getContractId());

        // Assert
        Optional<Contract> deletedContract = contractRepository.findById(savedContract.getContractId());
        assertFalse(deletedContract.isPresent());
    }

    @Test
    void testFindAllContracts() {
        // Arrange
        Contract contract1 = new Contract();
        contract1.setHotelName("Hotel A");
        contract1.setContractValidFrom(LocalDate.of(2023, 1, 1));
        contract1.setContractValidTill(LocalDate.of(2023, 12, 31));

        Contract contract2 = new Contract();
        contract2.setHotelName("Hotel B");
        contract2.setContractValidFrom(LocalDate.of(2024, 1, 1));
        contract2.setContractValidTill(LocalDate.of(2024, 12, 31));

        contractRepository.save(contract1);
        contractRepository.save(contract2);

        // Act
        List<Contract> contracts = contractRepository.findAll();

        // Assert
        assertEquals(2, contracts.size());
    }
}
