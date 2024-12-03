package com.sun_travels.web_backend.service;

import com.sun_travels.web_backend.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    @Autowired
    public ContractService(ContractRepository contractRepository)
    {
        this.contractRepository = contractRepository;
    }



}
