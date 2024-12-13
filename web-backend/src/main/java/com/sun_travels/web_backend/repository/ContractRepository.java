package com.sun_travels.web_backend.repository;

import com.sun_travels.web_backend.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("SELECT c FROM Contract c " +
            "WHERE c.contractValidFrom <= :checkInDate " +
            "AND c.contractValidTill >= :checkoutDate")
    List<Contract> findValidContracts(
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkoutDate") LocalDate checkoutDate
    );
}
