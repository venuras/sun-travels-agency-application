package com.sun_travels.web_backend.controller;

import com.sun_travels.web_backend.dto.ContractDto;
import com.sun_travels.web_backend.dto.request_body.DeleteContractRequestBody;
import com.sun_travels.web_backend.dto.request_body.GetAvailableHotelsRequestBody;
import com.sun_travels.web_backend.dto.response_body.AvailableHotelDto;
import com.sun_travels.web_backend.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contract")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService)
    {
        this.contractService = contractService;
    }

    @GetMapping
    public ResponseEntity<List<ContractDto>> getAllContracts()
    {
        return new ResponseEntity<>(contractService.getAllContracts(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ContractDto> addContract(@RequestBody ContractDto contractDto)
    {
        return new ResponseEntity<>(contractService.addContract(contractDto), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteContract(@RequestBody DeleteContractRequestBody reqBody)
    {
        contractService.deleteContract(reqBody.getContractId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/available")
    public ResponseEntity<List<AvailableHotelDto>> getAvailableHotels(@RequestBody GetAvailableHotelsRequestBody reqBody)
    {
        return new ResponseEntity<>(contractService.getAvailableHotels(reqBody), HttpStatus.OK);
    }
}
