package com.example.manageasset.infrastructure.lease.repositories;

import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.lease.repositories.LeaseContractRepository;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.shared.models.QueryFilter;
import com.example.manageasset.domain.shared.models.Status;
import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.infrastructure.user.repositories.UserEntity;
import com.google.common.base.Strings;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LeaseContractMySQLRepository implements LeaseContractRepository {
    private final AssetLeasedJpa assetLeasedJpa;
    private final LeaseContractJpa leaseContractJpa;
    private final EntityManager entityManager;

    public LeaseContractMySQLRepository(AssetLeasedJpa assetLeasedJpa, LeaseContractJpa leaseContractJpa,
                                        EntityManager entityManager) {
        this.assetLeasedJpa = assetLeasedJpa;
        this.leaseContractJpa = leaseContractJpa;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(LeaseContract leaseContract) {
        LeaseContractEntity leaseContractEntity = leaseContractJpa.save(LeaseContractEntity.fromModel(leaseContract));
        assetLeasedJpa.saveAll(leaseContract.getAssetLeaseds().stream().map(assetLeased -> AssetLeasedEntity.fromModel(assetLeased, leaseContractEntity)).collect(Collectors.toList()));
    }

    @Override
    public LeaseContract findById(String id) {
        LeaseContractEntity leaseContractEntity = leaseContractJpa.findByIdAndIsDeletedFalse(id);
        if (leaseContractEntity == null) return null;

        List<AssetLeasedEntity> assetLeasedEntities = assetLeasedJpa.findByLeaseContract_Id(id);
        LeaseContract leaseContract = leaseContractEntity.toModel();
        leaseContract.setAssetLeaseds(assetLeasedEntities.stream().map(AssetLeasedEntity::toModel).collect(Collectors.toList()));
        return leaseContract;
    }

    @Override
    public void deleteById(String id) {
        leaseContractJpa.deleteById(id, new Timestamp(Millisecond.now().asLong()));
    }

    @Override
    public List<LeaseContract> list(QueryFilter queryFilter, String searchText, Long leasedAtFrom, Long leasedAtTo, Status status, String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LeaseContractEntity> criteriaQuery = criteriaBuilder.createQuery(LeaseContractEntity.class);
        Root<LeaseContractEntity> root = criteriaQuery.from(LeaseContractEntity.class);
        Join<LeaseContractEntity, AssetLeasedEntity> assetLeased = root.join("assetLeaseds", JoinType.INNER);

        List<Predicate> listPredicateOr = new ArrayList<>();
        listPredicateOr.add(
                criteriaBuilder.like(root.get("reason"), "%" + searchText + "%")
        );
        listPredicateOr.add(
                criteriaBuilder.like(assetLeased.get("asset").get("name"), "%" + searchText + "%")
        );
        listPredicateOr.add(
                criteriaBuilder.like(root.get("client").get("fullName"), "%" + searchText + "%")
        );
        Predicate predicateOr
                = criteriaBuilder.or(listPredicateOr.toArray(new Predicate[0]));

        List<Predicate> listPredicateAnd = new ArrayList<>();

        listPredicateAnd.add(
                criteriaBuilder.equal(root.get("isDeleted"), false)
        );

        if (leasedAtFrom != null && leasedAtFrom >= 0) {
            listPredicateAnd.add(
                    criteriaBuilder.greaterThanOrEqualTo(root.get("leasedAt"), new Timestamp(leasedAtFrom))
            );
        }

        if (leasedAtTo != null && leasedAtTo >= 0) {
            listPredicateAnd.add(
                    criteriaBuilder.lessThanOrEqualTo(root.get("leasedAt"), new Timestamp(leasedAtTo))
            );
        }

        if(status != null){
            listPredicateAnd.add(
                    criteriaBuilder.equal(root.get("status"), status.asInt())
            );
        }

        if(username != null){
            listPredicateAnd.add(
                    criteriaBuilder.equal(root.get("client").get("username"), username)
            );
        }

        Predicate predicateAnd
                = criteriaBuilder.and(listPredicateAnd.toArray(new Predicate[0]));

        Predicate finalPredicate = criteriaBuilder.and(predicateAnd);
        if (!Strings.isNullOrEmpty(searchText)) {
            finalPredicate = criteriaBuilder.and(predicateAnd, predicateOr);
        }
        criteriaQuery.where(finalPredicate).orderBy(queryFilter.getSort().equals("asc") ? criteriaBuilder.asc(root.get("updatedAt")) : criteriaBuilder.desc(root.get("updatedAt"))).distinct(true);

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(queryFilter.getPage() * queryFilter.getLimit())
                .setMaxResults(queryFilter.getLimit())
                .getResultList()
                .stream().map(LeaseContractEntity::toModelWithAssetLeased).collect(Collectors.toList());
    }

    @Override
    public long totalList(String searchText, Long leasedAtFrom, Long leasedAtTo, Status status, String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<LeaseContractEntity> root = criteriaQuery.from(LeaseContractEntity.class);
        Join<LeaseContractEntity, AssetLeasedEntity> assetLeased = root.join("assetLeaseds", JoinType.INNER);

        List<Predicate> listPredicateOr = new ArrayList<>();
        listPredicateOr.add(
                criteriaBuilder.like(root.get("reason"), "%" + searchText + "%")
        );
        listPredicateOr.add(
                criteriaBuilder.like(assetLeased.get("asset").get("name"), "%" + searchText + "%")
        );
        listPredicateOr.add(
                criteriaBuilder.like(root.get("client").get("fullName"), "%" + searchText + "%")
        );
        Predicate predicateOr
                = criteriaBuilder.or(listPredicateOr.toArray(new Predicate[0]));

        List<Predicate> listPredicateAnd = new ArrayList<>();

        listPredicateAnd.add(
                criteriaBuilder.equal(root.get("isDeleted"), false)
        );

        if (leasedAtFrom != null && leasedAtFrom >= 0) {
            listPredicateAnd.add(
                    criteriaBuilder.greaterThanOrEqualTo(root.get("leasedAt"), new Timestamp(leasedAtFrom))
            );
        }

        if (leasedAtTo != null && leasedAtTo >= 0) {
            listPredicateAnd.add(
                    criteriaBuilder.lessThanOrEqualTo(root.get("leasedAt"), new Timestamp(leasedAtTo))
            );
        }

        if(status != null){
            listPredicateAnd.add(
                    criteriaBuilder.equal(root.get("status"), status.asInt())
            );
        }

        if(username != null){
            listPredicateAnd.add(
                    criteriaBuilder.equal(root.get("client").get("username"), username)
            );
        }

        Predicate predicateAnd
                = criteriaBuilder.and(listPredicateAnd.toArray(new Predicate[0]));

        Predicate finalPredicate = criteriaBuilder.and(predicateAnd);
        if (!Strings.isNullOrEmpty(searchText)) {
            finalPredicate = criteriaBuilder.and(predicateAnd, predicateOr);
        }

        criteriaQuery.select(criteriaBuilder.countDistinct(root)).where(finalPredicate);

        return entityManager.createQuery(criteriaQuery)
                .getSingleResult();
    }
}
