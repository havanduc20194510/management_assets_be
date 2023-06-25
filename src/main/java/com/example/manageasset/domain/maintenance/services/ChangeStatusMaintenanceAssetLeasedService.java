package com.example.manageasset.domain.maintenance.services;

import com.example.manageasset.domain.maintenance.dtos.MaintenanceAssetLeasedDto;
import com.example.manageasset.domain.maintenance.models.MaintenanceAssetLeased;
import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetLeasedRepository;
import com.example.manageasset.domain.shared.exceptions.InvalidRequestException;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeStatusMaintenanceAssetLeasedService {
    private final MaintenanceAssetLeasedRepository maintenanceAssetLeasedRepository;

    public void changeStatus(MaintenanceAssetLeasedDto maintenanceAssetLeasedDto) throws NotFoundException{
        MaintenanceAssetLeased maintenanceAssetLeased = maintenanceAssetLeasedRepository.getById(maintenanceAssetLeasedDto.getId());
        if (maintenanceAssetLeased == null)
            throw new NotFoundException(String.format("MaintenanceAssetLeased[id=%s] not found", maintenanceAssetLeasedDto.getId()));
        if(maintenanceAssetLeased.getStatus().asInt() != Status.INPROGRESS_TYPE){
            throw new InvalidRequestException(String.format("MaintenanceAssetLeased[id=%s] processed, cannot change status", maintenanceAssetLeasedDto.getId()));
        }
        if(maintenanceAssetLeasedDto.getStatus() != Status.REJECT_TYPE && maintenanceAssetLeasedDto.getStatus() != Status.APPROVED_TYPE){
            throw new InvalidRequestException(String.format("Value of status only 1[REJECT] or 2[APPROVED]"));
        }
        maintenanceAssetLeasedRepository.changeStatus(maintenanceAssetLeased.getId(), maintenanceAssetLeasedDto.getStatus());
    }
}
