package com.sun_travels.web_backend.utils;
import com.sun_travels.web_backend.dto.ContractDto;
import com.sun_travels.web_backend.dto.ContractRoomDto;
import com.sun_travels.web_backend.model.Contract;
import com.sun_travels.web_backend.model.ContractRoom;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppUtilsUnitTests {

    @Test
    void testDtoToEntityForContract() {
        ContractDto contractDto = new ContractDto();
        contractDto.setId(1L);
        contractDto.setHotelName("Hotel Sunshine");
        contractDto.setContractValidFrom(LocalDate.of(2023, 1, 1));
        contractDto.setContractValidTill(LocalDate.of(2023, 12, 31));
        ContractRoomDto roomDto = new ContractRoomDto();
        roomDto.setId(101L);
        roomDto.setRoomCount(2);
        roomDto.setRoomType("Deluxe");
        roomDto.setRoomPrice(150.0);
        roomDto.setMaxAdultCount(2);
        contractDto.setContractRooms(List.of(roomDto));

        Contract contract = AppUtils.ContractMapper.dtoToEntity(contractDto);

        assertNotNull(contract);
        assertEquals(contractDto.getId(), contract.getId());
        assertEquals(contractDto.getHotelName(), contract.getHotelName());
        assertEquals(contractDto.getContractValidFrom(), contract.getContractValidFrom());
        assertEquals(contractDto.getContractValidTill(), contract.getContractValidTill());
        assertEquals(contractDto.getContractRooms().size(), contract.getContractRooms().size());
    }

    @Test
    void testEntityToDtoForContract() {
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setHotelName("Hotel Sunshine");
        contract.setContractValidFrom(LocalDate.of(2023, 1, 1));
        contract.setContractValidTill(LocalDate.of(2023, 12, 31));
        ContractRoom room = new ContractRoom();
        room.setId(101L);
        room.setRoomCount(2);
        room.setRoomType("Deluxe");
        room.setRoomPrice(150.0);
        room.setMaxAdultCount(2);
        contract.setContractRooms(List.of(room));

        ContractDto contractDto = AppUtils.ContractMapper.entityToDto(contract);

        assertNotNull(contractDto);
        assertEquals(contract.getId(), contractDto.getId());
        assertEquals(contract.getHotelName(), contractDto.getHotelName());
        assertEquals(contract.getContractValidFrom(), contractDto.getContractValidFrom());
        assertEquals(contract.getContractValidTill(), contractDto.getContractValidTill());
        assertEquals(contract.getContractRooms().size(), contractDto.getContractRooms().size());
    }

    @Test
    void testDtoToEntityForContractRoom() {
        ContractRoomDto roomDto = new ContractRoomDto();
        roomDto.setId(101L);
        roomDto.setRoomCount(2);
        roomDto.setRoomType("Deluxe");
        roomDto.setRoomPrice(150.0);
        roomDto.setMaxAdultCount(2);

        ContractRoom contractRoom = AppUtils.ContractRoomMapper.dtoToEntity(roomDto);

        assertNotNull(contractRoom);
        assertEquals(roomDto.getId(), contractRoom.getId());
        assertEquals(roomDto.getRoomCount(), contractRoom.getRoomCount());
        assertEquals(roomDto.getRoomType(), contractRoom.getRoomType());
        assertEquals(roomDto.getRoomPrice(), contractRoom.getRoomPrice());
        assertEquals(roomDto.getMaxAdultCount(), contractRoom.getMaxAdultCount());
    }

    @Test
    void testEntityToDtoForContractRoom() {
        ContractRoom contractRoom = new ContractRoom();
        contractRoom.setId(101L);
        contractRoom.setRoomCount(2);
        contractRoom.setRoomType("Deluxe");
        contractRoom.setRoomPrice(150.0);
        contractRoom.setMaxAdultCount(2);

        ContractRoomDto roomDto = AppUtils.ContractRoomMapper.entityToDto(contractRoom);

        assertNotNull(roomDto);
        assertEquals(contractRoom.getId(), roomDto.getId());
        assertEquals(contractRoom.getRoomCount(), roomDto.getRoomCount());
        assertEquals(contractRoom.getRoomType(), roomDto.getRoomType());
        assertEquals(contractRoom.getRoomPrice(), roomDto.getRoomPrice());
        assertEquals(contractRoom.getMaxAdultCount(), roomDto.getMaxAdultCount());
    }

    @Test
    void testDtoToEntityForContractThrowsNullPointerExceptionWhenNullParameterIsPassed()
    {
        assertThrows(NullPointerException.class,() -> AppUtils.ContractMapper.dtoToEntity(null));
    }
}
