package com.sun_travels.web_backend.service;

import com.sun_travels.web_backend.dto.AvailableRoomTypeDto;
import com.sun_travels.web_backend.dto.RoomCountWithNoOfAdultsDto;
import com.sun_travels.web_backend.dto.RoomTypeWithPriceDto;
import com.sun_travels.web_backend.dto.request_body.GetAvailableHotelsRequestBody;
import com.sun_travels.web_backend.dto.response_body.AvailableHotelDto;
import com.sun_travels.web_backend.utils.AppUtils;
import com.sun_travels.web_backend.dto.ContractDto;
import com.sun_travels.web_backend.exception.ContractNotFoundException;
import com.sun_travels.web_backend.model.Contract;
import com.sun_travels.web_backend.repository.ContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ContractService {

    public static final Logger logger = LoggerFactory.getLogger(ContractService.class);

    private final ContractRepository contractRepository;

    @Autowired
    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public ContractDto addContract(ContractDto contractDto) {
        Contract contract = AppUtils.ContractMapper.dtoToEntity(contractDto);
        Contract saved = contractRepository.save(contract);
        return AppUtils.ContractMapper.entityToDto(saved);
    }

    public void deleteContract(Long id) {
        Optional<Contract> contractOptional = contractRepository.findById(id);
        contractOptional.ifPresentOrElse(value -> contractRepository.deleteById(id), () -> {
            throw new ContractNotFoundException(String.format("Unable to find contract with id %d", id));
        });
    }

    public List<ContractDto> getAllContracts() {
        return contractRepository.findAll().stream().map(AppUtils.ContractMapper::entityToDto).toList();
    }


    public List<AvailableHotelDto> getAvailableHotels(GetAvailableHotelsRequestBody reqBody) {
        LocalDate checkInDate = reqBody.getCheckInDate();
        LocalDate checkoutDate = checkInDate.plusDays(reqBody.getNoOfNights());
        List<RoomCountWithNoOfAdultsDto> roomCriteria = reqBody.getRoomCountWithNoOfAdults();

        // Fetch valid contracts based on check-in and check-out date
        List<Contract> validContracts = contractRepository.findValidContracts(checkInDate, checkoutDate);

        List<AvailableHotelDto> availableHotels = new ArrayList<>();

        for (Contract contract : validContracts) {
            // For each valid contract, filter contract details based on room and adult count criteria

            AtomicBoolean hotelCanFulfillCriterion = new AtomicBoolean(true);
            List<AvailableRoomTypeDto> availableRoomTypes =
                    roomCriteria.stream().map(
                            criteria -> {
                                List<RoomTypeWithPriceDto> possibleRoomTypes = contract.getContractDetails().stream()
                                        .filter(cd -> cd.getRoomCount() >= criteria.getRoomCount()
                                                && cd.getMaxAdultCount() == criteria.getAdultCount())
                                        .map(cd -> new RoomTypeWithPriceDto(
                                                cd.getRoomType(),
                                                cd.getRoomPrice() * (1 + contract.getMarkup() / 100) * reqBody.getNoOfNights() * cd.getMaxAdultCount() * criteria.getRoomCount()))  // Apply markup
                                        .toList();

                                if(possibleRoomTypes.isEmpty())
                                {
                                    hotelCanFulfillCriterion.set(false);
                                    return null;
                                }
                                else
                                {
                                    AvailableRoomTypeDto available = new AvailableRoomTypeDto();
                                    available.setCriteria(criteria);
                                    available.setAvailable(possibleRoomTypes);
                                    return available;
                                }
                            }
                    ).toList();

            if(hotelCanFulfillCriterion.get())
            {
                AvailableHotelDto hotelDto = new AvailableHotelDto(
                        contract.getContractId(),
                        contract.getHotelName(),
                        availableRoomTypes
                );
                availableHotels.add(hotelDto);
            }
        }

        return availableHotels;
    }
}
