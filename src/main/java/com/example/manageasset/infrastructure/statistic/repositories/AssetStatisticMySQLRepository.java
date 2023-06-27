package com.example.manageasset.infrastructure.statistic.repositories;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.shared.models.QueryFilter;
import com.example.manageasset.domain.statistic.models.AssetStatistic;
import com.example.manageasset.domain.statistic.repositories.AssetStatisticRepository;
import com.example.manageasset.infrastructure.asset.repositories.AssetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AssetStatisticMySQLRepository implements AssetStatisticRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private final AssetRepository assetRepository;

    @Override
    public List<AssetStatistic> getAll(QueryFilter queryFilter, String searchText, Long categoryId, Long from, Long to) {
        String sql = "SELECT a.id, COALESCE(tbl_a.tong_sl, 0) as total_quantity_not_process, COALESCE(tbl_b.tong_sl, 0) as total_quantity_leased" +
                " FROM assets as a" +
                " LEFT JOIN (" +
                " SELECT al.asset_id, SUM(al.quantity_lease) as tong_sl" +
                " FROM asset_leaseds as al" +
                " INNER JOIN lease_contracts as lc ON al.lease_contract_id = lc.id" +
                " WHERE lc.is_deleted = false" +
                " AND lc.status = 0" +
                " GROUP BY al.asset_id" +
                " ) as tbl_a ON a.id = tbl_a.asset_id" +
                " LEFT JOIN (" +
                " SELECT al.asset_id, SUM(al.quantity_lease) as tong_sl" +
                " FROM asset_leaseds as al" +
                " INNER JOIN lease_contracts as lc ON al.lease_contract_id = lc.id" +
                " WHERE lc.is_deleted = false" +
                " AND lc.status = 2" +
                " GROUP BY al.asset_id" +
                " ) as tbl_b ON a.id = tbl_b.asset_id" +
                " ORDER BY (COALESCE(tbl_a.tong_sl, 0) + COALESCE(tbl_b.tong_sl, 0)) " + queryFilter.getSort();

        Query query = entityManager.createNativeQuery(sql);

        query.setFirstResult(queryFilter.getPage() * queryFilter.getLimit());
        query.setMaxResults(queryFilter.getLimit());
        List<Object[]> records = query.getResultList();

        System.out.println(records.size());
        List<AssetStatistic> assetStatistics = new ArrayList<>();
        for(int i = 0; i < records.size(); i++){
            AssetStatistic assetStatistic = new AssetStatistic();
            Asset asset = assetRepository.getById(Long.parseLong(records.get(i)[0].toString()));
            assetStatistic.setAsset(asset);
            assetStatistic.setTotalQuantityNotProcess(Integer.parseInt(records.get(i)[1].toString()));
            assetStatistic.setTotalQuantityLeased(Integer.parseInt(records.get(i)[2].toString()));
            assetStatistics.add(assetStatistic);
        }
        return assetStatistics;
    }

    @Override
    public Long countTotal(String searchText, Long categoryId, Long from, Long to) {
        return 0L;
    }
}
