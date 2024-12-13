package com.sun_travels.web_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypeWithPriceDto {
    private String roomType;
    private double markedUpPrice;
}
