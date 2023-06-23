package com.example.manageasset.domain.asset.models;

import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.user.models.User;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asset {
    private Long id;
    private String name;
    private Integer quantity;
    private String status;
    private Double value;
    private String managementUnit;
    private Millisecond createdAt;
    private Millisecond updatedAt;
    private Boolean isDeleted;
    private User manager;
    private Category category;
    private List<Attachment> attachments;

    public Asset(String name, Integer quantity, String status, Double value, String managementUnit, Millisecond createdAt, Millisecond updatedAt, Boolean isDeleted, User manager, Category category, List<Attachment> attachments) {
        this.name = name;
        this.quantity = quantity;
        this.status = status;
        this.value = value;
        this.managementUnit = managementUnit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
        this.manager = manager;
        this.category = category;
        this.attachments = attachments;
    }

    public static Asset create(String name, Integer quantity, String status, Double value,
                               String managementUnit, User manager, Category category, List<Attachment> attachments) {
        validate(name, quantity, status, value, managementUnit, attachments);
        return new Asset(name, quantity, status, value, managementUnit, Millisecond.now(), Millisecond.now(), false, manager, category, attachments);
    }

    public void update(String name, Integer quantity, String status, Double value,
                               String managementUnit, Category category, List<Attachment> attachments) {
        validate(name, quantity, status, value, managementUnit, attachments);
        this.name = name;
        this.quantity = quantity;
        this.status = status;
        this.value = value;
        this.managementUnit = managementUnit;
        this.updatedAt = Millisecond.now();
        this.category = category;
        this.attachments = attachments;
    }

    private static void validate(String name, Integer quantity, String status, Double value, String managementUnit, List<Attachment> attachments) {
        if(Strings.isNullOrEmpty(name)){
            throw new InvalidDataException("Required field [name]");
        }
        if(quantity == null){
            throw new InvalidDataException("Required field [quantity]");
        }
        if(Strings.isNullOrEmpty(status)){
            throw new InvalidDataException("Required field [status]");
        }
        if(value == null){
            throw new InvalidDataException("Required field [value]");
        }
        if(Strings.isNullOrEmpty(managementUnit)){
            throw new InvalidDataException("Required field [managementUnit]");
        }
        if(CollectionUtils.isEmpty(attachments)){
            throw new InvalidDataException("Required field [attachments]");
        }
    }

    public void delete(){
        this.updatedAt = Millisecond.now();
        this.isDeleted = true;
    }
}
