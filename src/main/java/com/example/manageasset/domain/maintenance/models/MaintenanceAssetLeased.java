package com.example.manageasset.domain.maintenance.models;

import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.shared.models.Status;
import com.example.manageasset.domain.user.models.User;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceAssetLeased {
    private String id;
    private User client;
    private User user;
    private String reason;
    private Millisecond completedAt;
    private Millisecond startedAt;
    private String note;
    private List<AssetLeased> assetLeaseds;
    private Millisecond createdAt;
    private Millisecond updatedAt;
    private Boolean isDeleted;
    private Status status;

    public MaintenanceAssetLeased(String id, User client, User user, String reason, Millisecond completedAt, Millisecond startedAt, String note, Millisecond createdAt, Millisecond updatedAt, Boolean isDeleted) {
        this.id = id;
        this.client = client;
        this.user = user;
        this.reason = reason;
        this.completedAt = completedAt;
        this.startedAt = startedAt;
        this.note = note;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    public MaintenanceAssetLeased(String id, User client, User user, String reason, Millisecond completedAt, Millisecond startedAt, String note, Millisecond createdAt, Millisecond updatedAt, Boolean isDeleted, Status status) {
        this.id = id;
        this.client = client;
        this.user = user;
        this.reason = reason;
        this.completedAt = completedAt;
        this.startedAt = startedAt;
        this.note = note;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
        this.status = status;
    }

    public static MaintenanceAssetLeased create(String id, User client, User user, String reason, Millisecond completedAt, Millisecond startedAt, String note){
        validate(id, reason);
        return new MaintenanceAssetLeased(id, client, user, reason, completedAt, startedAt, note, Millisecond.now(), Millisecond.now(), false, Status.INPROGRESS);
    }

    public void update(User client, User user, String reason, Millisecond completedAt, Millisecond startedAt, String note){
        validate(reason);

        this.client = client;
        this.user = user;
        this.reason = reason;
        this.completedAt = completedAt;
        this.startedAt = startedAt;
        this.note = note;
        this.updatedAt = Millisecond.now();
    }

    private static void validate(String id, String reason){
        if(Strings.isNullOrEmpty(id)) throw new InvalidDataException("Required field MaintenanceAssetLeased[id]");
        if(Strings.isNullOrEmpty(reason)) throw new InvalidDataException("Required field MaintenanceAssetLeased[reason]");
    }

    private static void validate(String reason){
        if(Strings.isNullOrEmpty(reason)) throw new InvalidDataException("Required field MaintenanceAssetLeased[reason]");
    }
}
