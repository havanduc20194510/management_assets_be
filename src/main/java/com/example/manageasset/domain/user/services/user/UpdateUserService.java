package com.example.manageasset.domain.user.services.user;

import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.utility.Constants;
import com.example.manageasset.domain.shared.utility.ULID;
import com.example.manageasset.domain.user.dtos.UserDto;
import com.example.manageasset.domain.user.models.Department;
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
public class UpdateUserService {
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final Bucket bucket;

    public UpdateUserService(DepartmentRepository departmentRepository,
                             UserRepository userRepository,
                             Bucket bucket) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.bucket = bucket;
    }

    public void update(MultipartFile file, UserDto userDto) throws IOException, NotFoundException {
        Department department = departmentRepository.findById(userDto.getDepartment().getId());
        if(department == null) throw new NotFoundException(String.format("Department[id=%d] not found", userDto.getDepartment().getId()));

        User user = userRepository.findById(userDto.getId());
        if(user == null) throw new NotFoundException(String.format("User[id=%d] not found", userDto.getId()));

        if(file == null || file.isEmpty()){
            user.update(
                    userDto.getFullName(),
                    userDto.getAddress(),
                    userDto.getEmail(),
                    userDto.getMobile(),
                    userDto.getSex(),
                    userDto.getDateOfBirth(),
                    userDto.getPosition(),
                    null,
                    department
            );

            userRepository.save(user);
            return;
        }
        String nameFile = String.format("%s/%s", Constants.USER_FOLDER, new ULID().nextULID());

        Blob blob = bucket.create(nameFile, file.getBytes(), file.getContentType());
        user.update(
                userDto.getFullName(),
                userDto.getAddress(),
                userDto.getEmail(),
                userDto.getMobile(),
                userDto.getSex(),
                userDto.getDateOfBirth(),
                userDto.getPosition(),
                FirebaseStorageConfig.getURL(blob, nameFile).toString(),
                department
        );

        userRepository.save(user);
    }


}
