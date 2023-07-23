package com.example.manageasset.infrastructure.maintenance.controllers;

import com.example.manageasset.domain.maintenance.services.ChangeStatusMaintenanceAssetLeasedService;
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
public class ChangeStatusMaintenanceAssetLeasedController {
    private final ChangeStatusMaintenanceAssetLeasedService changeStatusMaintenanceAssetLeasedService;

    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateStatus(@RequestParam("status") Integer status,
                                          @PathVariable("id") String id) throws NotFoundException {
        changeStatusMaintenanceAssetLeasedService.updateStatus(status, id);
        PagingPayload.PagingPayloadBuilder<String> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data("Update status maintenance asset leased success");
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
