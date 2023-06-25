package com.example.manageasset.infrastructure.revoke.controllers;

import com.example.manageasset.domain.revoke.dtos.RevokeContractDto;
import com.example.manageasset.domain.revoke.services.UpdateRevokeContractService;
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
public class UpdateRevokeContractController {
    private final UpdateRevokeContractService updateRevokeContractService;

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody RevokeContractDto revokeContractDto) throws NotFoundException {
        updateRevokeContractService.update(revokeContractDto);
        PagingPayload.PagingPayloadBuilder<String> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data("Update revoke contract success");
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
