package com.example.manageasset.domain.maintenance.services;

import com.example.manageasset.domain.maintenance.models.MaintenanceAssetLeased;
import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetLeasedRepository;
import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetRepository;
import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteMaintenanceAssetLeasedService {
    private final MaintenanceAssetLeasedRepository maintenanceAssetLeasedRepository;
    private final MaintenanceAssetRepository maintenanceAssetRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(String id) throws NotFoundException {
        MaintenanceAssetLeased maintenanceAssetLeased = maintenanceAssetLeasedRepository.getById(id);
        if (maintenanceAssetLeased == null)
            throw new NotFoundException(String.format("MaintenanceAssetLeased[id=%s] not found", id));

        String username = "cuongpm";
        if(!maintenanceAssetLeased.getClient().getUsername().equals(username))
            throw new InvalidDataException("Client deleting this maintenance asset leased is not client create");
        
        maintenanceAssetLeasedRepository.delete(id);
        maintenanceAssetRepository.deleteAllByMaintenanceId(id);
    }
}
