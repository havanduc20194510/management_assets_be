package com.example.manageasset.domain.maintenance.services;

import com.example.manageasset.domain.lease.dtos.AssetLeasedDto;
import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.repositories.AssetLeasedRepository;
import com.example.manageasset.domain.maintenance.dtos.MaintenanceAssetLeasedDto;
import com.example.manageasset.domain.maintenance.models.MaintenanceAssetLeased;
import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetLeasedRepository;
import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetRepository;
import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.exceptions.InvalidRequestException;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.domain.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateMaintenanceAssetLeasedService {
    private final MaintenanceAssetLeasedRepository maintenanceAssetLeasedRepository;
    private final MaintenanceAssetRepository maintenanceAssetRepository;
    private final AssetLeasedRepository assetLeasedRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void update(MaintenanceAssetLeasedDto maintenanceAssetLeasedDto) throws NotFoundException {
        MaintenanceAssetLeased maintenanceAssetLeased = maintenanceAssetLeasedRepository.getById(maintenanceAssetLeasedDto.getId());
        if (maintenanceAssetLeased == null)
            throw new NotFoundException(String.format("MaintenanceAssetLeased[id=%s] not found", maintenanceAssetLeasedDto.getId()));

        String username = "cuongpm";
        if(!maintenanceAssetLeased.getClient().getUsername().equals(username))
            throw new InvalidDataException("Client updating this maintenance asset leased is not client create");

        if(CollectionUtils.isEmpty(maintenanceAssetLeasedDto.getAssetLeasedDtos())){
            throw new InvalidRequestException("List asset leased cannot empty");
        }

        if(maintenanceAssetLeased.getStatus().asInt() > 0){
            throw new InvalidRequestException(String.format("MaintenanceAssetLeased[id=%s] processed, cannot update", maintenanceAssetLeasedDto.getId()));
        }

        List<AssetLeased> assetLeaseds = new ArrayList<>();
        for (AssetLeasedDto assetLeasedDto : maintenanceAssetLeasedDto.getAssetLeasedDtos()) {
            AssetLeased assetLeased = assetLeasedRepository.findById(assetLeasedDto.getId());
            if (assetLeased == null)
                throw new NotFoundException(String.format("AssetLeased[id=%d] not found", assetLeasedDto.getId()));
            assetLeaseds.add(assetLeased);
        }

        maintenanceAssetLeased.update(maintenanceAssetLeasedDto.getReason(), new Millisecond(maintenanceAssetLeasedDto.getCompletedAt()), new Millisecond(maintenanceAssetLeasedDto.getStartedAt()), maintenanceAssetLeasedDto.getNote());
        maintenanceAssetLeased.setAssetLeaseds(assetLeaseds);
        maintenanceAssetRepository.deleteAllByMaintenanceId(maintenanceAssetLeasedDto.getId());
        maintenanceAssetLeasedRepository.save(maintenanceAssetLeased);
    }
}
