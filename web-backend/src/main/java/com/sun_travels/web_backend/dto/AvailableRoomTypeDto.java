package com.sun_travels.web_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableRoomTypeDto {
    private RoomCountWithNoOfAdultsDto criteria;
    private List<RoomTypeWithPriceDto> available;
}
