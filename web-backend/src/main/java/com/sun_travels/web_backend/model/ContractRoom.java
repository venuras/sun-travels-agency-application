package com.sun_travels.web_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ContractRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomType;
    private double roomPrice;
    private int roomCount;
    private int maxAdultCount;

}
