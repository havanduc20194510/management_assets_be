package com.example.manageasset.infrastructure.user.controllers.user;


import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import com.example.manageasset.domain.user.dtos.UserDto;
import com.example.manageasset.domain.user.services.user.UpdateUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UpdateUserController {
    private final UpdateUserService updateUserService;

    public UpdateUserController(UpdateUserService updateUserService) {
        this.updateUserService = updateUserService;
    }

    @PutMapping(value = "/update", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> update(@RequestPart("user") UserDto userDto,
                                    @RequestPart(value = "file", required = false) MultipartFile file) throws NotFoundException, IOException {
        updateUserService.update(file, userDto);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
