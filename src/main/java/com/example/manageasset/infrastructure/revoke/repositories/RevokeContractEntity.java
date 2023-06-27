package com.example.manageasset.infrastructure.revoke.repositories;

import com.example.manageasset.domain.revoke.models.RevokeContract;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.infrastructure.lease.repositories.LeaseContractEntity;
import com.example.manageasset.infrastructure.user.repositories.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "revoke_contracts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RevokeContractEntity {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "reason", nullable = false, length = 1000)
    private String reason;
    @Column(name = "revoked_at", nullable = false)
    private Timestamp revokedAt;
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
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lease_contract_id", nullable = false)
    private LeaseContractEntity leaseContract;

    public static RevokeContractEntity fromModel(RevokeContract revokeContract){
        return new RevokeContractEntity(
                revokeContract.getId(),
                revokeContract.getReason(),
                new Timestamp(revokeContract.getRevokedAt().asLong()),
                revokeContract.getNote(),
                new Timestamp(revokeContract.getCreatedAt().asLong()),
                new Timestamp(revokeContract.getUpdatedAt().asLong()),
                revokeContract.getIsDeleted(),
                UserEntity.fromModel(revokeContract.getUser()),
                UserEntity.fromModel(revokeContract.getClient()),
                LeaseContractEntity.fromModel(revokeContract.getLeaseContract())
        );
    }

    public RevokeContract toModel(){
        return new RevokeContract(
                id,
                client.toModel(),
                user.toModel(),
                reason,
                new Millisecond(revokedAt.getTime()),
                note,
                leaseContract.toModel(),
                new Millisecond(createdAt.getTime()),
                new Millisecond(updatedAt.getTime()),
                isDeleted
        );
    }

}
