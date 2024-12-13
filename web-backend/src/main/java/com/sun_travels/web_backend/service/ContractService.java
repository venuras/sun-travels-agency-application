package com.sun_travels.web_backend.service;

import com.sun_travels.web_backend.dto.AvailableRoomTypeDto;
import com.sun_travels.web_backend.dto.RoomCountWithNoOfAdultsDto;
import com.sun_travels.web_backend.dto.RoomTypeWithPriceDto;
import com.sun_travels.web_backend.dto.request_body.GetAvailableHotelsRequestBody;
import com.sun_travels.web_backend.dto.response_body.AvailableHotelDto;
import com.sun_travels.web_backend.model.ContractDetail;
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
        LocalDate checkoutDate = calculateCheckoutDate(checkInDate, reqBody.getNoOfNights());
        List<RoomCountWithNoOfAdultsDto> roomCriteria = reqBody.getRoomCountWithNoOfAdults();

        // Fetch valid contracts based on check-in and check-out date
        List<Contract> validContracts = fetchValidContracts(checkInDate, checkoutDate);

        return buildAvailableHotels(validContracts, roomCriteria, reqBody);
    }

    private LocalDate calculateCheckoutDate(LocalDate checkInDate, int noOfNights) {
        return checkInDate.plusDays(noOfNights);
    }

    private List<Contract> fetchValidContracts(LocalDate checkInDate, LocalDate checkoutDate) {
        return contractRepository.findValidContracts(checkInDate, checkoutDate);
    }

    private List<AvailableHotelDto> buildAvailableHotels(List<Contract> validContracts,
                                                         List<RoomCountWithNoOfAdultsDto> roomCriteria,
                                                         GetAvailableHotelsRequestBody reqBody) {
        List<AvailableHotelDto> availableHotels = new ArrayList<>();

        for (Contract contract : validContracts) {
            List<AvailableRoomTypeDto> availableRoomTypes = getAvailableRoomTypesForContract(contract, roomCriteria, reqBody);
            if (!availableRoomTypes.isEmpty()) {
                availableHotels.add(createAvailableHotelDto(contract, availableRoomTypes));
            }
        }

        return availableHotels;
    }

    private List<AvailableRoomTypeDto> getAvailableRoomTypesForContract(Contract contract,
                                                                        List<RoomCountWithNoOfAdultsDto> roomCriteria,
                                                                        GetAvailableHotelsRequestBody reqBody) {
        AtomicBoolean hotelCanFulfillCriterion = new AtomicBoolean(true);

        List<AvailableRoomTypeDto> availableRoomTypes = roomCriteria.stream()
                .map(criteria -> mapCriteriaToRoomType(contract, criteria, reqBody, hotelCanFulfillCriterion))
                .filter(Objects::nonNull) // Exclude nulls (criteria not fulfilled)
                .toList();

        return hotelCanFulfillCriterion.get() ? availableRoomTypes : Collections.emptyList();
    }

    private AvailableRoomTypeDto mapCriteriaToRoomType(Contract contract,
                                                       RoomCountWithNoOfAdultsDto criteria,
                                                       GetAvailableHotelsRequestBody reqBody,
                                                       AtomicBoolean hotelCanFulfillCriterion) {
        List<RoomTypeWithPriceDto> possibleRoomTypes = findPossibleRoomTypes(contract, criteria, reqBody);

        if (possibleRoomTypes.isEmpty()) {
            hotelCanFulfillCriterion.set(false);
            return null;
        }

        AvailableRoomTypeDto availableRoomTypeDto = new AvailableRoomTypeDto();
        availableRoomTypeDto.setCriteria(criteria);
        availableRoomTypeDto.setAvailable(possibleRoomTypes);
        return availableRoomTypeDto;
    }

    private List<RoomTypeWithPriceDto> findPossibleRoomTypes(Contract contract,
                                                             RoomCountWithNoOfAdultsDto criteria,
                                                             GetAvailableHotelsRequestBody reqBody) {
        return contract.getContractDetails().stream()
                .filter(cd -> cd.getRoomCount() >= criteria.getRoomCount() &&
                        cd.getMaxAdultCount() == criteria.getAdultCount())
                .map(cd -> new RoomTypeWithPriceDto(
                        cd.getRoomType(),
                        calculateRoomPrice(cd, contract, reqBody)))
                .toList();
    }

    private double calculateRoomPrice(ContractDetail cd, Contract contract, GetAvailableHotelsRequestBody reqBody) {
        return Math.ceil(cd.getRoomPrice() *
                (1 + contract.getMarkup() / 100) *
                reqBody.getNoOfNights() *
                cd.getMaxAdultCount() *
                cd.getRoomCount());
    }

    private AvailableHotelDto createAvailableHotelDto(Contract contract, List<AvailableRoomTypeDto> availableRoomTypes) {
        return new AvailableHotelDto(contract.getContractId(), contract.getHotelName(), availableRoomTypes);
    }

}
