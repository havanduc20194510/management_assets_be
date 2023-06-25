package com.example.manageasset.domain.maintenance.services;

import com.example.manageasset.domain.lease.dtos.AssetLeasedDto;
import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.repositories.AssetLeasedRepository;
import com.example.manageasset.domain.maintenance.dtos.MaintenanceAssetLeasedDto;
import com.example.manageasset.domain.maintenance.models.MaintenanceAssetLeased;
import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetLeasedRepository;
import com.example.manageasset.domain.shared.exceptions.InvalidRequestException;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.shared.models.Status;
import com.example.manageasset.domain.shared.utility.ULID;
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
public class CreateMaintenanceAssetLeasedService {
    private final MaintenanceAssetLeasedRepository maintenanceAssetLeasedRepository;
    private final UserRepository userRepository;
    private final AssetLeasedRepository assetLeasedRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void create(MaintenanceAssetLeasedDto maintenanceAssetLeasedDto) throws NotFoundException {
        User user = userRepository.findById(maintenanceAssetLeasedDto.getUserDto().getId());
        if (user == null)
            throw new NotFoundException(String.format("User[id=%d] not found", maintenanceAssetLeasedDto.getUserDto().getId()));
        User client = userRepository.findById(maintenanceAssetLeasedDto.getClientDto().getId());
        if (client == null)
            throw new NotFoundException(String.format("Client[id=%d] not found", maintenanceAssetLeasedDto.getClientDto().getId()));
        if(CollectionUtils.isEmpty(maintenanceAssetLeasedDto.getAssetLeasedDtos())){
            throw new InvalidRequestException("List asset leased cannot empty");
        }
        MaintenanceAssetLeased maintenanceAssetLeased = MaintenanceAssetLeased.create(new ULID().nextULID(), client, user, maintenanceAssetLeasedDto.getReason(), new Millisecond(maintenanceAssetLeasedDto.getCompletedAt()), new Millisecond(maintenanceAssetLeasedDto.getStartedAt()), maintenanceAssetLeasedDto.getNote());

        List<AssetLeased> assetLeaseds = new ArrayList<>();
        for (AssetLeasedDto assetLeasedDto : maintenanceAssetLeasedDto.getAssetLeasedDtos()) {
            AssetLeased assetLeased = assetLeasedRepository.findById(assetLeasedDto.getId());
            if (assetLeased == null)
                throw new NotFoundException(String.format("AssetLeased[id=%d] not found", assetLeasedDto.getId()));
            assetLeaseds.add(assetLeased);
        }

        maintenanceAssetLeased.setAssetLeaseds(assetLeaseds);
        maintenanceAssetLeasedRepository.save(maintenanceAssetLeased);
    }
}
