package com.sun_travels.web_backend.repository;

import com.sun_travels.web_backend.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
