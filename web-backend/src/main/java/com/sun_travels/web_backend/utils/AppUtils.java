package com.sun_travels.web_backend.utils;

import com.sun_travels.web_backend.dto.ContractDto;
import com.sun_travels.web_backend.dto.ContractRoomDto;
import com.sun_travels.web_backend.model.Contract;
import com.sun_travels.web_backend.model.ContractRoom;

public class AppUtils {

    private AppUtils(){

    }
    public static class ContractMapper {

        private ContractMapper() {
        }

        public static Contract dtoToEntity(ContractDto contractDto) {
            Contract contract = new Contract();
            contract.setId(contractDto.getId());
            contract.setHotelName(contractDto.getHotelName());
            contract.setContractValidFrom(contractDto.getContractValidFrom());
            contract.setContractValidTill(contractDto.getContractValidTill());
            contract.setContractRooms(contractDto.getContractRooms().stream().map(ContractRoomMapper::dtoToEntity).toList());
            return contract;
        }

        public static ContractDto entityToDto(Contract contract) {
            ContractDto dto = new ContractDto();
            dto.setId(contract.getId());
            dto.setHotelName(contract.getHotelName());
            dto.setContractValidFrom(contract.getContractValidFrom());
            dto.setContractValidTill(contract.getContractValidTill());
            dto.setContractRooms(contract.getContractRooms().stream().map(ContractRoomMapper::entityToDto).toList());
            return dto;
        }
    }

    public static class ContractRoomMapper {

        private ContractRoomMapper() {
        }

        public static ContractRoom dtoToEntity(ContractRoomDto roomDto) {
            ContractRoom contractRoom = new ContractRoom();
            contractRoom.setId(roomDto.getId());
            contractRoom.setRoomCount(roomDto.getRoomCount());
            contractRoom.setRoomPrice(roomDto.getRoomPrice());
            contractRoom.setRoomType(roomDto.getRoomType());
            contractRoom.setMaxAdultCount(roomDto.getMaxAdultCount());
            return contractRoom;
        }

        public static ContractRoomDto entityToDto(ContractRoom contractRoom) {
            ContractRoomDto dto = new ContractRoomDto();
            dto.setId(contractRoom.getId());
            dto.setRoomCount(contractRoom.getRoomCount());
            dto.setRoomType(contractRoom.getRoomType());
            dto.setRoomPrice(contractRoom.getRoomPrice());
            dto.setMaxAdultCount(contractRoom.getMaxAdultCount());
            return dto;
        }
    }
}
