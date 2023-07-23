package com.example.manageasset.infrastructure.maintenance.controllers;

import com.example.manageasset.domain.maintenance.dtos.MaintenanceAssetLeasedDto;
import com.example.manageasset.domain.maintenance.services.GetMaintenanceAssetLeasedService;
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
public class GetMaintenanceAssetLeasedController {
    private final GetMaintenanceAssetLeasedService getMaintenanceAssetLeasedService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) throws NotFoundException {
        MaintenanceAssetLeasedDto maintenanceAssetLeasedDto = getMaintenanceAssetLeasedService.get(id);
        PagingPayload.PagingPayloadBuilder<MaintenanceAssetLeasedDto> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(maintenanceAssetLeasedDto);
        return new ResponseEntity<>(new com.example.manageasset.domain.shared.models.ResponseBody(payloadBuilder.build(), com.example.manageasset.domain.shared.models.ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
