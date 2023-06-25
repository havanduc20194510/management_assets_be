package com.example.manageasset.infrastructure.revoke.controllers;

import com.example.manageasset.domain.revoke.dtos.RevokeContractDto;
import com.example.manageasset.domain.revoke.services.CreateRevokeContractService;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/revoke-contract")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CreateRevokeContractController {
    private final CreateRevokeContractService createRevokeContractService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody RevokeContractDto revokeContractDto) throws NotFoundException {
        createRevokeContractService.create(revokeContractDto);
        PagingPayload.PagingPayloadBuilder<String> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data("Create revoke contract success");
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
