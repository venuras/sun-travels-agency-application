package com.sun_travels.web_backend.service;

import com.sun_travels.web_backend.utils.AppUtils;
import com.sun_travels.web_backend.dto.ContractDto;
import com.sun_travels.web_backend.exception.ContractNotFoundException;
import com.sun_travels.web_backend.model.Contract;
import com.sun_travels.web_backend.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    @Autowired
    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public ContractDto addContract(ContractDto contractDto) {
        Contract contract = AppUtils.ContractMapper.dtoToEntity(contractDto);
        Contract saved = contractRepository.save(contract);
        return AppUtils.ContractMapper.entityToDto(saved);
    }

    public void deleteContract(Long id) {
        Optional<Contract> contractOptional = contractRepository.findById(id);
        contractOptional.ifPresentOrElse(value -> contractRepository.deleteById(id), () -> {
            throw new ContractNotFoundException(String.format("Unable to find contract with id %d", id));
        });
    }

    public List<ContractDto> getAllContracts() {
        return contractRepository.findAll().stream().map(AppUtils.ContractMapper::entityToDto).toList();
    }
}
