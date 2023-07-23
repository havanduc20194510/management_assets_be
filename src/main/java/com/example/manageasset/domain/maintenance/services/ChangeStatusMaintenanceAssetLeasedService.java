package com.example.manageasset.domain.maintenance.services;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.models.State;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.repositories.AssetLeasedRepository;
import com.example.manageasset.domain.maintenance.models.MaintenanceAssetLeased;
import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetLeasedRepository;
import com.example.manageasset.domain.shared.exceptions.InvalidRequestException;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.Status;
import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.domain.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChangeStatusMaintenanceAssetLeasedService {
    private final MaintenanceAssetLeasedRepository maintenanceAssetLeasedRepository;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final AssetLeasedRepository assetLeasedRepository;

    public void updateStatus(Integer status, String id) throws NotFoundException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        MaintenanceAssetLeased maintenanceAssetLeased = maintenanceAssetLeasedRepository.getById(id);
        if (maintenanceAssetLeased == null)
            throw new NotFoundException(String.format("MaintenanceAssetLeased[id=%s] not found", id));
        if(maintenanceAssetLeased.getStatus().asInt() != Status.INPROGRESS_TYPE){
            throw new InvalidRequestException(String.format("MaintenanceAssetLeased[id=%s] processed, cannot change status", id));
        }
        if(status != Status.REJECT_TYPE && status != Status.APPROVED_TYPE){
            throw new InvalidRequestException(String.format("Value of status only 1[REJECT] or 2[APPROVED]"));
        }

        if(status == Status.REJECT_TYPE){
            List<AssetLeased> assetLeaseds = assetLeasedRepository.findByMaintenanceId(id);
            assetLeaseds.forEach(assetLeased -> {
                Asset asset = assetLeased.getAsset();
                asset.updateState(State.NOT_READY);
                assetRepository.save(asset);
            });
        }
        maintenanceAssetLeased.updateStatus(new Status(status), user);
    }
}
