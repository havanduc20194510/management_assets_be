package com.example.manageasset.domain.lease.services;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.models.State;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.lease.dtos.AssetLeasedDto;
import com.example.manageasset.domain.lease.dtos.LeaseContractDto;
import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.lease.repositories.AssetLeasedRepository;
import com.example.manageasset.domain.lease.repositories.LeaseContractRepository;
import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.shared.models.Status;
import com.example.manageasset.domain.shared.utility.ULID;
import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.domain.user.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateLeaseContractService {
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;
    private final LeaseContractRepository leaseContractRepository;
    private final AssetLeasedRepository assetLeasedRepository;
    private final CheckProcessLeaseContractService checkProcessLeaseContractService;


    public UpdateLeaseContractService(AssetRepository assetRepository, UserRepository userRepository, LeaseContractRepository leaseContractRepository, AssetLeasedRepository assetLeasedRepository, CheckProcessLeaseContractService checkProcessLeaseContractService) {
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
        this.leaseContractRepository = leaseContractRepository;
        this.assetLeasedRepository = assetLeasedRepository;
        this.checkProcessLeaseContractService = checkProcessLeaseContractService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void update(LeaseContractDto leaseContractDto) throws NotFoundException {
        LeaseContract leaseContract = leaseContractRepository.findById(leaseContractDto.getId());
        if (leaseContract == null) throw new NotFoundException(String.format("LeaseContract[id=%s] not found", leaseContractDto.getId()));

        checkProcessLeaseContractService.check(leaseContract);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if(!leaseContract.getClient().getUsername().equals(username)) throw new  InvalidDataException("Client is updating lease contract is not client create lease contract");

        leaseContract.update(leaseContractDto.getReason(), new Millisecond(leaseContractDto.getRevokedAt()), new Millisecond(leaseContractDto.getLeasedAt()), leaseContractDto.getNote());

        List<AssetLeased> assetLeaseds = new ArrayList<>();
        for (AssetLeasedDto assetLeasedDto : leaseContractDto.getAssetLeasedDtos()) {
            Asset asset = assetRepository.getById(assetLeasedDto.getAssetDto().getId());
            if (asset == null)
                throw new NotFoundException(String.format("Asset[id=%d] not found", assetLeasedDto.getAssetDto().getId()));

            assetLeaseds.add(AssetLeased.create(new ULID().nextULID(), asset));
        }
        leaseContract.setAssetLeaseds(assetLeaseds);

        assetLeasedRepository.deleteAllByLeaseContractId(leaseContract.getId());

        leaseContractRepository.save(leaseContract);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateStatus(String id, Integer status) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        LeaseContract leaseContract = leaseContractRepository.findById(id);
        if (leaseContract == null) throw new NotFoundException(String.format("LeaseContract[id=%s] not found", id));

        checkProcessLeaseContractService.check(leaseContract);

        leaseContract.update(new Status(status), user);
        if(status == Status.REJECT_TYPE){
            leaseContract.getAssetLeaseds().forEach(assetLeased -> {
                Asset asset = assetLeased.getAsset();
                asset.updateState(State.READY);
                assetRepository.save(asset);
            });
        }

        leaseContractRepository.save(leaseContract);
    }
}
