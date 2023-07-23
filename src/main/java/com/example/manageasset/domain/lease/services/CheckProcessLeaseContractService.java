package com.example.manageasset.domain.lease.services;

import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetRepository;
import com.example.manageasset.domain.revoke.repositories.RevokeContractRepository;
import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import org.springframework.stereotype.Service;

@Service
public class CheckProcessLeaseContractService {
    private final RevokeContractRepository revokeContractRepository;
    private final MaintenanceAssetRepository maintenanceAssetRepository;

    public CheckProcessLeaseContractService(RevokeContractRepository revokeContractRepository,
                                            MaintenanceAssetRepository maintenanceAssetRepository) {
        this.revokeContractRepository = revokeContractRepository;
        this.maintenanceAssetRepository = maintenanceAssetRepository;
    }

    public void check(LeaseContract leaseContract){
        if(revokeContractRepository.existedRevokeForLease(leaseContract.getId())){
            throw new InvalidDataException(String.format("LeaseContract[id=%s] was revoked, not delete or update lease contract", leaseContract.getId()));
        }

        for (AssetLeased assetLeased : leaseContract.getAssetLeaseds()){
            if(maintenanceAssetRepository.existedMaintenanceAssetLeased(assetLeased.getAssetCode()))
                throw new InvalidDataException(String.format("LeaseContract[id=%s] had asset leased maintenance, not delete or update lease contract", leaseContract.getId()));
        }
    }
}
