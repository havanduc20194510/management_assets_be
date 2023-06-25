package com.example.manageasset.infrastructure.lease.controllers;

import com.example.manageasset.domain.lease.dtos.LeaseContractDto;
import com.example.manageasset.domain.lease.services.GetLeaseContractService;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lease-contract")
public class GetLeaseContractController {
    private final GetLeaseContractService getLeaseContractService;

    public GetLeaseContractController(GetLeaseContractService getLeaseContractService) {
        this.getLeaseContractService = getLeaseContractService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) throws NotFoundException {
        PagingPayload.PagingPayloadBuilder<LeaseContractDto> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(getLeaseContractService.get(id));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
