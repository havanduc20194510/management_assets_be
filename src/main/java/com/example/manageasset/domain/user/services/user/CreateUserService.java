package com.example.manageasset.domain.user.services.user;

import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.utility.Constants;
import com.example.manageasset.domain.shared.utility.ULID;
import com.example.manageasset.domain.user.dtos.UserDto;
import com.example.manageasset.domain.user.models.Department;
import com.example.manageasset.domain.user.models.Role;
import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.domain.user.repositories.DepartmentRepository;
import com.example.manageasset.domain.user.repositories.UserRepository;
import com.example.manageasset.infrastructure.shared.configs.FirebaseStorageConfig;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CreateUserService {
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final Bucket bucket;

    public CreateUserService(DepartmentRepository departmentRepository,
                             UserRepository userRepository,
                             Bucket bucket) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.bucket = bucket;
    }

    public void create(MultipartFile file, UserDto userDto) throws IOException, NotFoundException {
        Department department = departmentRepository.findById(userDto.getDepartment().getId());
        if(department == null) throw new NotFoundException(String.format("Department[id=%d] not found", userDto.getDepartment().getId()));

        User userCheck = userRepository.findByUsername(userDto.getUsername());
        if(userCheck != null) throw new InvalidDataException("Username is exist");

        String nameFile = String.format("%s/%s", Constants.USER_FOLDER, new ULID().nextULID());

        Blob blob = bucket.create(nameFile, file.getBytes(), file.getContentType());

        User user = User.create(
                userDto.getFullName(),
                userDto.getAddress(),
                userDto.getEmail(),
                userDto.getMobile(),
                userDto.getSex(),
                userDto.getDateOfBirth(),
                userDto.getUsername(),
                userDto.getPassword(),
                Role.fromValue(userDto.getPosition()).name(),
                FirebaseStorageConfig.getURL(blob, nameFile).toString(),
                department
        );

        userRepository.save(user);
    }


}
