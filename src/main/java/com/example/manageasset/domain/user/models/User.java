package com.example.manageasset.domain.user.models;

import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String fullName;
    private String address;
    private String email;
    private String mobile;
    private Boolean sex;
    private String dateOfBirth;
    private String username;
    private String password;
    private String position;
    private String avatar;
    private Millisecond createdAt;
    private Millisecond updatedAt;
    private Boolean isDeleted;

    public User(String fullName, String address, String email, String mobile, Boolean sex, String dateOfBirth, String username, String password, String position, String avatar, Millisecond createdAt, Millisecond updatedAt, Boolean isDeleted) {
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
        this.position = position;
        this.avatar = avatar;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    public static User create(String fullName, String address, String email, String mobile, Boolean sex, String dateOfBirth, String username, String password, String position, String avatar){
        validate(fullName, address ,email, mobile, sex, dateOfBirth, username, password, position, avatar);
        return new User(fullName, address, email, mobile, sex, dateOfBirth, username, password, position, avatar, Millisecond.now(), Millisecond.now(), false);
    }

    public static void validate(String fullName, String address, String email, String mobile, Boolean sex, String dateOfBirth, String username, String password, String position, String avatar){
        if(Strings.isNullOrEmpty(fullName)) throw new InvalidDataException("Required field User[fullName]");
        if(Strings.isNullOrEmpty(address)) throw new InvalidDataException("Required field User[address]");
        if(Strings.isNullOrEmpty(email)) throw new InvalidDataException("Required field User[email]");
        if(Strings.isNullOrEmpty(mobile)) throw new InvalidDataException("Required field User[mobile]");
        if(sex == null) throw new InvalidDataException("Required field User[sex]");
        if(Strings.isNullOrEmpty(dateOfBirth)) throw new InvalidDataException("Required field User[dateOfBirth]");
        if(Strings.isNullOrEmpty(username)) throw new InvalidDataException("Required field User[username]");
        if(Strings.isNullOrEmpty(password)) throw new InvalidDataException("Required field User[password]");
        if(Strings.isNullOrEmpty(position)) throw new InvalidDataException("Required field User[position]");
        if(Strings.isNullOrEmpty(avatar)) throw new InvalidDataException("Required field User[avatar]");
    }
}
