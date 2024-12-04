package com.sun_travels.web_backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class ContractDto {
    private Long id;

    @NotBlank(message = "Hotel name cannot be blank")
    @NotNull(message = "Hotel name cannot be null")
    private String hotelName;

    @NotNull(message = "Contract valid start date cannot be null")
    private LocalDate contractValidFrom;

    @NotNull(message = "Contract valid end date cannot be null")
    private LocalDate contractValidTill;

    @NotNull(message = "Room List cannot be null")
    @NotEmpty(message = "Contract must have at least one type of room")
    private List<ContractRoomDto> contractRooms;
}
