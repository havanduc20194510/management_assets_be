package com.example.manageasset.infrastructure.lease.controllers;

import com.example.manageasset.domain.lease.dtos.LeaseContractDto;
import com.example.manageasset.domain.lease.services.UpdateLeaseContractService;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/lease-contract")
public class UpdateLeaseContractController {
    private final UpdateLeaseContractService updateLeaseContractService;

    public UpdateLeaseContractController(UpdateLeaseContractService updateLeaseContractService) {
        this.updateLeaseContractService = updateLeaseContractService;
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody LeaseContractDto leaseContractDto) throws NotFoundException {
        updateLeaseContractService.update(leaseContractDto);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
