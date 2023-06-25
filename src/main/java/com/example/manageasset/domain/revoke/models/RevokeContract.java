package com.example.manageasset.domain.revoke.models;

import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.user.models.User;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevokeContract {
    private String id;
    private User client;
    private User user;
    private String reason;
    private Millisecond revokedAt;
    private String note;
    private LeaseContract leaseContract;
    private Millisecond createdAt;
    private Millisecond updatedAt;
    private Boolean isDeleted;

    public static RevokeContract create(String id, User client, User user, String reason, Millisecond revokedAt, String note, LeaseContract leaseContract){
        validate(id, reason);
        return new RevokeContract(id, client, user, reason, revokedAt, note, leaseContract, Millisecond.now(), Millisecond.now(), false);
    }

    public void update(User client, User user, String reason, Millisecond revokedAt, String note){
        validate(reason);
        this.client = client;
        this.user = user;
        this.reason = reason;
        this.revokedAt = revokedAt;
        this.note = note;

        this.updatedAt = Millisecond.now();
    }

    private static void validate(String id, String reason){
        if(Strings.isNullOrEmpty(id)) throw new InvalidDataException("Required field RevokeContract[id]");
        if(Strings.isNullOrEmpty(reason)) throw new InvalidDataException("Required field RevokeContract[reason]");
    }

    private static void validate(String reason){
        if(Strings.isNullOrEmpty(reason)) throw new InvalidDataException("Required field RevokeContract[reason]");
    }
}
