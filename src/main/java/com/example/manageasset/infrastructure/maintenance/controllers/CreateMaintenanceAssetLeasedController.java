package com.example.manageasset.infrastructure.maintenance.controllers;

import com.example.manageasset.domain.maintenance.dtos.MaintenanceAssetLeasedDto;
import com.example.manageasset.domain.maintenance.services.CreateMaintenanceAssetLeasedService;
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
public class CreateMaintenanceAssetLeasedController {
    private final CreateMaintenanceAssetLeasedService createMaintenanceAssetLeasedService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody MaintenanceAssetLeasedDto maintenanceAssetLeasedDto) throws NotFoundException {
        createMaintenanceAssetLeasedService.create(maintenanceAssetLeasedDto);
        PagingPayload.PagingPayloadBuilder<String> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data("Create maintenance asset leased success");
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
