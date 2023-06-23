package com.example.manageasset.domain.user.models;

import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    private Long id;
    private String name;
    private Millisecond createdAt;
    private Millisecond updatedAt;
    private Boolean isDeleted;

    public Department(String name, Millisecond createdAt, Millisecond updatedAt, Boolean isDeleted) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    public static Department create(String name) {
        validate(name);
        return new Department(name, Millisecond.now(), Millisecond.now(), false);

    }

    private static void validate(String name) {
        if (Strings.isNullOrEmpty(name)) {
            throw new InvalidDataException("Required field Department[name]");
        }
    }

    public void update(String name){
        validate(name);
        this.name = name;
        this.updatedAt = Millisecond.now();
    }

    public void delete(){
        this.updatedAt = Millisecond.now();
        this.isDeleted = true;
    }
}
