package com.example.manageasset.domain.asset.models;

import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.models.Millisecond;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Long id;
    private String name;
    private Millisecond createdAt;
    private Millisecond updatedAt;
    private Boolean isDeleted;

    public Category(String name, Millisecond createdAt, Millisecond updatedAt, Boolean isDeleted) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    public static Category create(String name) {
        validate(name);
        return new Category(name, Millisecond.now(), Millisecond.now(), false);
    }

    public void update(String name) {
        validate(name);
        this.name = name;
        this.updatedAt = Millisecond.now();
    }

    private static void validate(String name) {
        if (!StringUtils.hasLength(name)) {
            throw new InvalidDataException("Required field [name]");
        }
    }
}
