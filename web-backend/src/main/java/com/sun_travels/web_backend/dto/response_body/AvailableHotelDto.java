package com.sun_travels.web_backend.dto.response_body;

import com.sun_travels.web_backend.dto.AvailableRoomTypeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableHotelDto {
    private Long contractId;
    private String hotelName;
    private List<AvailableRoomTypeDto> availableRoomTypesForCriterion;
}
