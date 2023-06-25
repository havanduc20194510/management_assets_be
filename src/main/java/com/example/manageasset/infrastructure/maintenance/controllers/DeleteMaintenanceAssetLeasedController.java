package com.example.manageasset.infrastructure.maintenance.controllers;

import com.example.manageasset.domain.maintenance.services.DeleteMaintenanceAssetLeasedService;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maintenance-asset-leased")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DeleteMaintenanceAssetLeasedController {
    private final DeleteMaintenanceAssetLeasedService deleteMaintenanceAssetLeasedService;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) throws NotFoundException {
        deleteMaintenanceAssetLeasedService.delete(id);
        PagingPayload.PagingPayloadBuilder<String> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data("Delete maintenance asset leased success");
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
