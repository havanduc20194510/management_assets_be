package com.example.manageasset.infrastructure.lease.repositories;

import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.shared.models.Status;
import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.infrastructure.user.repositories.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "lease_contracts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaseContractEntity {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "reason", nullable = false, length = 1000)
    private String reason;
    @Column(name = "revoked_at", nullable = false)
    private Timestamp revokedAt;
    @Column(name = "leased_at", nullable = false)
    private Timestamp leasedAt;
    @Column(name = "note", length = 1000)
    private String note;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private UserEntity client;
    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "leaseContract", fetch = FetchType.LAZY)
    private List<AssetLeasedEntity> assetLeaseds;


    public LeaseContractEntity(String id, String reason, Timestamp revokedAt, Timestamp leasedAt, String note, Timestamp createdAt, Timestamp updatedAt, Boolean isDeleted, UserEntity user, UserEntity client, Integer status) {
        this.id = id;
        this.reason = reason;
        this.revokedAt = revokedAt;
        this.leasedAt = leasedAt;
        this.note = note;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
        this.user = user;
        this.client = client;
        this.status = status;
    }

    public static LeaseContractEntity fromModel(LeaseContract leaseContract){
        return new LeaseContractEntity(
                leaseContract.getId(),
                leaseContract.getReason(),
                new Timestamp(leaseContract.getRevokedAt().asLong()),
                new Timestamp(leaseContract.getLeasedAt().asLong()),
                leaseContract.getNote(),
                new Timestamp(leaseContract.getCreatedAt().asLong()),
                new Timestamp(leaseContract.getUpdatedAt().asLong()),
                leaseContract.getIsDeleted(),
                UserEntity.fromModel(leaseContract.getUser()),
                UserEntity.fromModel(leaseContract.getClient()),
                leaseContract.getStatus().asInt()
        );
    }

    public LeaseContract toModel(){
        return new LeaseContract(
                id,
                client.toModel(),
                user == null ? null : user.toModel(),
                reason,
                new Millisecond(revokedAt.getTime()),
                new Millisecond(leasedAt.getTime()),
                note,
                new Millisecond(createdAt.getTime()),
                new Millisecond(updatedAt.getTime()),
                isDeleted,
                new Status(status)
        );
    }

    public LeaseContract toModelWithAssetLeased(){
        return new LeaseContract(
                id,
                client.toModel(),
                user == null ? null : user.toModel(),
                reason,
                new Millisecond(revokedAt.getTime()),
                new Millisecond(leasedAt.getTime()),
                note,
                assetLeaseds.stream().map(AssetLeasedEntity::toModel).collect(Collectors.toList()),
                new Millisecond(createdAt.getTime()),
                new Millisecond(updatedAt.getTime()),
                isDeleted,
                new Status(status)
        );
    }
}
