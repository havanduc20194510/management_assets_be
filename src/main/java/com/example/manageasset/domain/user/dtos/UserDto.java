package com.example.manageasset.domain.user.dtos;

import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.user.models.Department;
import com.example.manageasset.domain.user.models.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    @JsonProperty("full_name")
    private String fullName;
    private String address;
    private String email;
    private String mobile;
    private Boolean sex;
    @JsonProperty("date_of_birth")
    private String dateOfBirth;
    private String username;
    private String password;
    private String position;
    private String avatar;
    private DepartmentDto department;


    public UserDto(Long id, String fullName, String address, String email, String mobile, Boolean sex, String dateOfBirth, String username, String position, String avatar, DepartmentDto department) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.position = position;
        this.avatar = avatar;
        this.department = department;
    }

    public static UserDto fromModel(User user){
        return new UserDto(user.getId(), user.getFullName(), user.getAddress(), user.getEmail(), user.getMobile(), user.getSex(), user.getDateOfBirth(), user.getUsername(), user.getPosition(), user.getAvatar(), DepartmentDto.fromModel(user.getDepartment()));
    }
}
