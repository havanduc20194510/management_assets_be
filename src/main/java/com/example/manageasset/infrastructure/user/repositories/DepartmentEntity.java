package com.example.manageasset.infrastructure.user.repositories;

import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.user.models.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentEntity {
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

    public static DepartmentEntity fromModel(Department department){
        return new DepartmentEntity(
                department.getId(),
                department.getName(),
                new Timestamp(department.getCreatedAt().asLong()),
                new Timestamp(department.getUpdatedAt().asLong()),
                department.getIsDeleted());
    }

    public Department toModel(){
        return new Department(id, name, new Millisecond(createdAt.getTime()), new Millisecond(updatedAt.getTime()), isDeleted);
    }

}
