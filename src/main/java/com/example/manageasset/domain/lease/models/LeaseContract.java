package com.example.manageasset.domain.lease.models;

import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaseContract {
    private String id;
    private User client;
    private User user;
    private String reason;
    private Millisecond revokedAt;
    private Millisecond leasedAt;
    private String note;
    private List<AssetLeased> assetLeaseds;
    private Millisecond createdAt;
    private Millisecond updatedAt;
    private Boolean isDeleted;

    public LeaseContract(String id, User client, User user, String reason, Millisecond revokedAt, Millisecond leasedAt, String note, Millisecond createdAt, Millisecond updatedAt, Boolean isDeleted) {
        this.id = id;
        this.client = client;
        this.user = user;
        this.reason = reason;
        this.revokedAt = revokedAt;
        this.leasedAt = leasedAt;
        this.note = note;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }
}
