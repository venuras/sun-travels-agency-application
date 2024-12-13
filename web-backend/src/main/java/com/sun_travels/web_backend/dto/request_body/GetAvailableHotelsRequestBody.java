package com.sun_travels.web_backend.dto.request_body;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun_travels.web_backend.dto.RoomCountWithNoOfAdultsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAvailableHotelsRequestBody {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkInDate;
    private int noOfNights;
    private List<RoomCountWithNoOfAdultsDto> roomCountWithNoOfAdults;
}
