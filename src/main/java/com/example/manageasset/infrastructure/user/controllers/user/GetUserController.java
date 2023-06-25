package com.example.manageasset.infrastructure.user.controllers.user;

import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import com.example.manageasset.domain.user.dtos.DepartmentDto;
import com.example.manageasset.domain.user.dtos.UserDto;
import com.example.manageasset.domain.user.services.user.GetUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class GetUserController {
    private final GetUserService getUserService;

    public GetUserController(GetUserService getUserService) {
        this.getUserService = getUserService;
    }

    @GetMapping(value = "/get-by-id/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) throws NotFoundException {
        PagingPayload.PagingPayloadBuilder<UserDto> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(getUserService.get(id));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

    @GetMapping(value = "/get-by-username/{username}")
    public ResponseEntity<?> get(@PathVariable("username") String username) throws NotFoundException {
        PagingPayload.PagingPayloadBuilder<UserDto> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(getUserService.get(username));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
