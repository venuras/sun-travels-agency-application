package com.sun_travels.web_backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class ContractDetailDto {
    private Long id;

    @NotBlank
    private String roomType;
    private double roomPrice;
    private int roomCount;
    private int maxAdultCount;
}
