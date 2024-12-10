package com.sun_travels.web_backend.utils;

import com.sun_travels.web_backend.dto.ContractDto;
import com.sun_travels.web_backend.dto.ContractDetailDto;
import com.sun_travels.web_backend.model.Contract;
import com.sun_travels.web_backend.model.ContractDetail;

public class AppUtils {

    private AppUtils(){

    }

    private static class DtoValidator{
        public static void validateContractDto(){}
    }

    public static class ContractMapper {

        private ContractMapper() {
        }


        public static Contract dtoToEntity(ContractDto contractDto) {

            Contract contract = new Contract();
            contract.setContractId(contractDto.getContractId());
            contract.setHotelName(contractDto.getHotelName());
            contract.setContractValidFrom(contractDto.getContractValidFrom());
            contract.setContractValidTill(contractDto.getContractValidTill());
            contract.setContractDetails(contractDto.getContractDetails().stream().map(ContractDetailMapper::dtoToEntity).toList());
            contract.setCreatedAt(contractDto.getCreatedAt());
            contract.setMarkup(contractDto.getMarkup());
            return contract;
        }

        public static ContractDto entityToDto(Contract contract) {
            ContractDto dto = new ContractDto();
            dto.setContractId(contract.getContractId());
            dto.setHotelName(contract.getHotelName());
            dto.setContractValidFrom(contract.getContractValidFrom());
            dto.setContractValidTill(contract.getContractValidTill());
            dto.setContractDetails(contract.getContractDetails().stream().map(ContractDetailMapper::entityToDto).toList());
            dto.setCreatedAt(contract.getCreatedAt());
            dto.setMarkup(contract.getMarkup());
            return dto;
        }
    }

    public static class ContractDetailMapper {

        private ContractDetailMapper() {
        }

        public static ContractDetail dtoToEntity(ContractDetailDto contractDetailDto) {
            ContractDetail contractDetail = new ContractDetail();
            contractDetail.setContractDetailId(contractDetailDto.getId());
            contractDetail.setRoomCount(contractDetailDto.getRoomCount());
            contractDetail.setRoomPrice(contractDetailDto.getRoomPrice());
            contractDetail.setRoomType(contractDetailDto.getRoomType());
            contractDetail.setMaxAdultCount(contractDetailDto.getMaxAdultCount());
            return contractDetail;
        }

        public static ContractDetailDto entityToDto(ContractDetail contractDetail) {
            ContractDetailDto dto = new ContractDetailDto();
            dto.setId(contractDetail.getContractDetailId());
            dto.setRoomCount(contractDetail.getRoomCount());
            dto.setRoomType(contractDetail.getRoomType());
            dto.setRoomPrice(contractDetail.getRoomPrice());
            dto.setMaxAdultCount(contractDetail.getMaxAdultCount());
            return dto;
        }
    }
}
