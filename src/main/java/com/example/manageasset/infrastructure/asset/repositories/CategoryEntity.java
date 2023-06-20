package com.example.manageasset.infrastructure.asset.repositories;

import com.example.manageasset.domain.asset.models.Category;
import com.example.manageasset.domain.shared.models.Millisecond;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    public static CategoryEntity fromDomain(Category category) {
        return new CategoryEntity(
                category.getId(),
                category.getName(),
                new Timestamp(category.getCreatedAt().asLong()),
                new Timestamp(category.getUpdatedAt().asLong()),
                category.getIsDeleted()
        );
    }

    public Category toDomain() {
        return new Category(id, name, new Millisecond(createdAt.getTime()), new Millisecond(updatedAt.getTime()), isDeleted);
    }
}
