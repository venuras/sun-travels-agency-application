package com.sun_travels.web_backend.utils;
import com.sun_travels.web_backend.dto.ContractDto;
import com.sun_travels.web_backend.dto.ContractDetailDto;
import com.sun_travels.web_backend.model.Contract;
import com.sun_travels.web_backend.model.ContractDetail;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppUtilsUnitTests {

    @Test
    void testDtoToEntityForContract() {
        ContractDto contractDto = new ContractDto();
        contractDto.setContractId(1L);
        contractDto.setHotelName("Hotel Sunshine");
        contractDto.setContractValidFrom(LocalDate.of(2023, 1, 1));
        contractDto.setContractValidTill(LocalDate.of(2023, 12, 31));
        ContractDetailDto contractDetailDto = new ContractDetailDto();
        contractDetailDto.setId(101L);
        contractDetailDto.setRoomCount(2);
        contractDetailDto.setRoomType("Deluxe");
        contractDetailDto.setRoomPrice(150.0);
        contractDetailDto.setMaxAdultCount(2);
        contractDto.setContractDetails(List.of(contractDetailDto));

        Contract contract = AppUtils.ContractMapper.dtoToEntity(contractDto);

        assertNotNull(contract);
        assertEquals(contractDto.getContractId(), contract.getContractId());
        assertEquals(contractDto.getHotelName(), contract.getHotelName());
        assertEquals(contractDto.getContractValidFrom(), contract.getContractValidFrom());
        assertEquals(contractDto.getContractValidTill(), contract.getContractValidTill());
        assertEquals(contractDto.getContractDetails().size(), contract.getContractDetails().size());
    }

    @Test
    void testEntityToDtoForContract() {
        Contract contract = new Contract();
        contract.setContractId(1L);
        contract.setHotelName("Hotel Sunshine");
        contract.setContractValidFrom(LocalDate.of(2023, 1, 1));
        contract.setContractValidTill(LocalDate.of(2023, 12, 31));
        ContractDetail contractDetail = new ContractDetail();
        contractDetail.setContractDetailId(101L);
        contractDetail.setRoomCount(2);
        contractDetail.setRoomType("Deluxe");
        contractDetail.setRoomPrice(150.0);
        contractDetail.setMaxAdultCount(2);
        contract.setContractDetails(List.of(contractDetail));

        ContractDto contractDto = AppUtils.ContractMapper.entityToDto(contract);

        assertNotNull(contractDto);
        assertEquals(contract.getContractId(), contractDto.getContractId());
        assertEquals(contract.getHotelName(), contractDto.getHotelName());
        assertEquals(contract.getContractValidFrom(), contractDto.getContractValidFrom());
        assertEquals(contract.getContractValidTill(), contractDto.getContractValidTill());
        assertEquals(contract.getContractDetails().size(), contractDto.getContractDetails().size());
    }

    @Test
    void testDtoToEntityForContractRoom() {
        ContractDetailDto contractDetailDto = new ContractDetailDto();
        contractDetailDto.setId(101L);
        contractDetailDto.setRoomCount(2);
        contractDetailDto.setRoomType("Deluxe");
        contractDetailDto.setRoomPrice(150.0);
        contractDetailDto.setMaxAdultCount(2);

        ContractDetail contractDetail = AppUtils.ContractDetailMapper.dtoToEntity(contractDetailDto);

        assertNotNull(contractDetail);
        assertEquals(contractDetailDto.getId(), contractDetail.getContractDetailId());
        assertEquals(contractDetailDto.getRoomCount(), contractDetail.getRoomCount());
        assertEquals(contractDetailDto.getRoomType(), contractDetail.getRoomType());
        assertEquals(contractDetailDto.getRoomPrice(), contractDetail.getRoomPrice());
        assertEquals(contractDetailDto.getMaxAdultCount(), contractDetail.getMaxAdultCount());
    }

    @Test
    void testEntityToDtoForContractRoom() {
        ContractDetail contractDetail = new ContractDetail();
        contractDetail.setContractDetailId(101L);
        contractDetail.setRoomCount(2);
        contractDetail.setRoomType("Deluxe");
        contractDetail.setRoomPrice(150.0);
        contractDetail.setMaxAdultCount(2);

        ContractDetailDto contractDetailDto = AppUtils.ContractDetailMapper.entityToDto(contractDetail);

        assertNotNull(contractDetailDto);
        assertEquals(contractDetail.getContractDetailId(), contractDetailDto.getId());
        assertEquals(contractDetail.getRoomCount(), contractDetailDto.getRoomCount());
        assertEquals(contractDetail.getRoomType(), contractDetailDto.getRoomType());
        assertEquals(contractDetail.getRoomPrice(), contractDetailDto.getRoomPrice());
        assertEquals(contractDetail.getMaxAdultCount(), contractDetailDto.getMaxAdultCount());
    }

    @Test
    void testDtoToEntityForContractThrowsNullPointerExceptionWhenNullParameterIsPassed()
    {
        assertThrows(NullPointerException.class,() -> AppUtils.ContractMapper.dtoToEntity(null));
    }
}
