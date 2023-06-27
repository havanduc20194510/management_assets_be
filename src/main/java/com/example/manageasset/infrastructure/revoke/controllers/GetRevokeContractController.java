package com.example.manageasset.infrastructure.revoke.controllers;

import com.example.manageasset.domain.revoke.dtos.RevokeContractDto;
import com.example.manageasset.domain.revoke.services.GetRevokeContractService;
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
public class GetRevokeContractController {
    private final GetRevokeContractService getRevokeContractService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) throws NotFoundException {
        RevokeContractDto revokeContractDto = getRevokeContractService.get(id);
        PagingPayload.PagingPayloadBuilder<RevokeContractDto> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(revokeContractDto);
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
