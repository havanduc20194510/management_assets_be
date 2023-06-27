package com.example.manageasset.infrastructure.maintenance.repositories;

import com.example.manageasset.domain.maintenance.models.MaintenanceAssetLeased;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.shared.models.Status;
import com.example.manageasset.infrastructure.user.repositories.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "maintenance_asset_leaseds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceAssetLeasedEntity {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "reason", nullable = false, length = 1000)
    private String reason;
    @Column(name = "completed_at", nullable = false)
    private Timestamp completedAt;
    @Column(name = "started_at", nullable = false)
    private Timestamp startedAt;
    @Column(name = "note", length = 1000)
    private String note;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;
    @Column(name = "status", nullable = false)
    private Integer status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private UserEntity client;

    public static MaintenanceAssetLeasedEntity fromModel(MaintenanceAssetLeased maintenanceAssetLeased){
        return new MaintenanceAssetLeasedEntity(
                maintenanceAssetLeased.getId(),
                maintenanceAssetLeased.getReason(),
                new Timestamp(maintenanceAssetLeased.getCompletedAt().asLong()),
                new Timestamp(maintenanceAssetLeased.getStartedAt().asLong()),
                maintenanceAssetLeased.getNote(),
                new Timestamp(maintenanceAssetLeased.getCreatedAt().asLong()),
                new Timestamp(maintenanceAssetLeased.getUpdatedAt().asLong()),
                maintenanceAssetLeased.getIsDeleted(),
                maintenanceAssetLeased.getStatus().asInt(),
                maintenanceAssetLeased.getUser() == null ? null : UserEntity.fromModel(maintenanceAssetLeased.getUser()),
                UserEntity.fromModel(maintenanceAssetLeased.getClient())
        );
    }

    public MaintenanceAssetLeased toModel(){
        return new MaintenanceAssetLeased(
                id,
                client.toModel(),
                user == null ? null : user.toModel(),
                reason,
                new Millisecond(completedAt.getTime()),
                new Millisecond(startedAt.getTime()),
                note,
                new Millisecond(createdAt.getTime()),
                new Millisecond(updatedAt.getTime()),
                isDeleted,
                new Status(status)
        );
    }
}
