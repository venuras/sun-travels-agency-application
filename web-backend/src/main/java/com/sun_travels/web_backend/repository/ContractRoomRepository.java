package com.sun_travels.web_backend.repository;

import com.sun_travels.web_backend.model.ContractRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRoomRepository extends JpaRepository<ContractRoom, Long> {
}
