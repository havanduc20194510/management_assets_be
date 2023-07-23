package com.example.manageasset.infrastructure.lease.controllers;

import com.example.manageasset.domain.lease.services.DeleteLeaseContractService;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/lease-contract")
public class DeleteLeaseContractController {
    private final DeleteLeaseContractService deleteLeaseContractService;

    public DeleteLeaseContractController(DeleteLeaseContractService deleteLeaseContractService) {
        this.deleteLeaseContractService = deleteLeaseContractService;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) throws NotFoundException {
        deleteLeaseContractService.delete(id);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
