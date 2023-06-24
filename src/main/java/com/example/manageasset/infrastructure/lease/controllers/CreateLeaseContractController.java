package com.example.manageasset.infrastructure.lease.controllers;

import com.example.manageasset.domain.asset.dtos.CategoryDto;
import com.example.manageasset.domain.lease.dtos.LeaseContractDto;
import com.example.manageasset.domain.lease.services.CreateLeaseContractService;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lease-contract")
public class CreateLeaseContractController {
    private final CreateLeaseContractService createLeaseContractService;

    public CreateLeaseContractController(CreateLeaseContractService createLeaseContractService) {
        this.createLeaseContractService = createLeaseContractService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody LeaseContractDto leaseContractDto) throws NotFoundException {
        createLeaseContractService.create(leaseContractDto);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
