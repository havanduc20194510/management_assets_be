package com.example.manageasset.infrastructure.user.repositories;

import com.example.manageasset.domain.user.models.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "mobile", nullable = false)
    private String mobile;
    @Column(name = "sex", nullable = false)
    private Boolean sex;
    @Column(name = "date_of_birth", nullable = false)
    private String dateOfBirth;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "position", nullable = false)
    private String position;
    @Column(name = "avatar", nullable = false, length = 1000)
    private String avatar;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity department;

    public static UserEntity fromModel(User user){
        return new UserEntity(
                user.getId(),
                user.getFullName(),
                user.getAddress(),
                user.getEmail(),
                user.getMobile(),
                user.getSex(),
                user.getDateOfBirth(),
                user.getUsername(),
                user.getPassword(),
                user.getPosition(),
                user.getAvatar(),
                new Timestamp(user.getCreatedAt().asLong()),
                new Timestamp(user.getUpdatedAt().asLong()),
                user.getIsDeleted(),
                DepartmentEntity.fromModel(user.getDepartment())
        );
    }
}
