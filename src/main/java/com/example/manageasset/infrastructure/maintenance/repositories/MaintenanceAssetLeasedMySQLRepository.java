package com.example.manageasset.infrastructure.maintenance.repositories;

import com.example.manageasset.domain.maintenance.models.MaintenanceAsset;
import com.example.manageasset.domain.maintenance.models.MaintenanceAssetLeased;
import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetLeasedRepository;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.shared.models.QueryFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MaintenanceAssetLeasedMySQLRepository implements MaintenanceAssetLeasedRepository {
    private final MaintenanceAssetLeasedJpa maintenanceAssetLeasedJpa;
    private final MaintenanceAssetJpa maintenanceAssetJpa;

    @Override
    @Transactional
    public void save(MaintenanceAssetLeased maintenanceAssetLeased) {
        MaintenanceAssetLeasedEntity maintenanceAssetLeasedEntity = MaintenanceAssetLeasedEntity.fromModel(maintenanceAssetLeased);
        maintenanceAssetLeasedEntity = maintenanceAssetLeasedJpa.save(maintenanceAssetLeasedEntity);
        maintenanceAssetLeased.setId(maintenanceAssetLeasedEntity.getId());

        maintenanceAssetJpa.saveAll(maintenanceAssetLeased.getAssetLeaseds().stream().map(assetLeased -> {
            MaintenanceAsset maintenanceAsset = MaintenanceAsset.create(maintenanceAssetLeased, assetLeased);
            return MaintenanceAssetEntity.fromModel(maintenanceAsset);
        }).collect(Collectors.toList()));
    }

    @Override
    public List<MaintenanceAssetLeased> getAll(QueryFilter queryFilter, Long from, Long to, String searchText) {
        Pageable pageable = PageRequest.of(queryFilter.getPage(), queryFilter.getLimit(),
                queryFilter.getSort().equals("asc") ? Sort.by("startedAt").ascending() : Sort.by("startedAt").descending());
        Timestamp fromDate = null;
        if(from != null){
            fromDate = new Timestamp(from);
        }
        Timestamp toDate = null;
        if(to != null){
            toDate = new Timestamp(to);
        }
        return maintenanceAssetLeasedJpa.findAll(pageable, fromDate, toDate, searchText).stream().map(MaintenanceAssetLeasedEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public Long countTotal(Long from, Long to, String searchText) {
        Timestamp fromDate = null;
        if(from != null){
            fromDate = new Timestamp(from);
        }
        Timestamp toDate = null;
        if(to != null){
            toDate = new Timestamp(to);
        }
        return maintenanceAssetLeasedJpa.countTotal(fromDate, toDate, searchText);
    }

    @Override
    public MaintenanceAssetLeased getById(String id) {
        Optional<MaintenanceAssetLeasedEntity> opt = maintenanceAssetLeasedJpa.findById(id);
        return opt.map(MaintenanceAssetLeasedEntity::toModel).orElse(null);
    }

    @Override
    public void delete(String id) {
        maintenanceAssetLeasedJpa.deleteById(id, new Timestamp(Millisecond.now().asLong()));
    }
}
