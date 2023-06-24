package com.example.manageasset.infrastructure.lease.repositories;

import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.infrastructure.user.repositories.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private UserEntity client;

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
                UserEntity.fromModel(leaseContract.getClient())
        );
    }

    public LeaseContract toModel(){
        return new LeaseContract(
                id,
                client.toModel(),
                user.toModel(),
                reason,
                new Millisecond(revokedAt.getTime()),
                new Millisecond(leasedAt.getTime()),
                note,
                new Millisecond(createdAt.getTime()),
                new Millisecond(updatedAt.getTime()),
                isDeleted
        );
    }
}
