package com.example.manageasset.infrastructure.revoke.controllers;

import com.example.manageasset.domain.revoke.services.DeleteRevokeContractService;
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
public class DeleteRevokeContractController {
    private final DeleteRevokeContractService deleteRevokeContractService;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) throws NotFoundException {
        deleteRevokeContractService.delete(id);
        PagingPayload.PagingPayloadBuilder<String> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data("Delete revoke contract success");
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
