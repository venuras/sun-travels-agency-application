package com.sun_travels.web_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;

    @Column(nullable = false)
    private String hotelName;

    @Column(nullable = false)
    private LocalDate contractValidFrom;

    @Column(nullable = false)
    private LocalDate contractValidTill;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "contract_id", referencedColumnName = "contractId")
    private List<ContractDetail> contractDetails;

    @Column(nullable = false)
    @ColumnDefault("15.00")
    private double markup;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
