package com.example.manageasset.domain.lease.services;

import com.example.manageasset.domain.asset.models.Asset;
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
import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.domain.user.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        String username = "cuongpm";
        if(!leaseContract.getClient().getUsername().equals(username)) throw new  InvalidDataException("Client is updating lease contract is not client create lease contract");

        leaseContract.update(leaseContractDto.getReason(), new Millisecond(leaseContractDto.getRevokedAt()), new Millisecond(leaseContractDto.getLeasedAt()), leaseContractDto.getNote());

        List<AssetLeased> assetLeaseds = new ArrayList<>();
        for (AssetLeasedDto assetLeasedDto : leaseContractDto.getAssetLeasedDtos()) {
            Asset asset = assetRepository.getById(assetLeasedDto.getAssetDto().getId());
            if (asset == null)
                throw new NotFoundException(String.format("Asset[id=%d] not found", assetLeasedDto.getAssetDto().getId()));

            int quantity = asset.getQuantity();
            AssetLeased assetLeasedExist = findById(asset.getId(), leaseContract.getAssetLeaseds());
            if (assetLeasedExist != null) quantity += assetLeasedExist.getQuantityLease();

            if (quantity < assetLeasedDto.getQuantityLease()) {
                throw new InvalidDataException(String.format("Asset[id=%d] have inventory quantity less leased quantity", assetLeasedDto.getAssetDto().getId()));
            }
            assetLeaseds.add(AssetLeased.create(assetLeasedDto.getId(), assetLeasedDto.getQuantityLease(), asset));
            assetRepository.updateQuantity(quantity - assetLeasedDto.getQuantityLease(), asset.getId());
        }
        leaseContract.setAssetLeaseds(assetLeaseds);

        assetLeasedRepository.deleteAllByLeaseContractId(leaseContract.getId());

        leaseContractRepository.save(leaseContract);


    }

    private AssetLeased findById(Long assetId, List<AssetLeased> assetLeaseds){
        return assetLeaseds.stream().filter(assetLeased -> Objects.equals(assetLeased.getAsset().getId(), assetId)).findAny().orElse(null);
    }

    public void updateStatus(String id, Integer status) throws NotFoundException {
        String username = "cuongpm";
        User user = userRepository.findByUsername(username);

        LeaseContract leaseContract = leaseContractRepository.findById(id);
        if (leaseContract == null) throw new NotFoundException(String.format("LeaseContract[id=%s] not found", id));

        leaseContract.update(new Status(status), user);

        leaseContractRepository.save(leaseContract);
    }
}
