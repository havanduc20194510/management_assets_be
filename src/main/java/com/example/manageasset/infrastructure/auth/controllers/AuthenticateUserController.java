package com.example.manageasset.infrastructure.auth.controllers;

import com.example.manageasset.domain.auth.LoginUserService;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = "*")
public class AuthenticateUserController {
    private final LoginUserService loginUserService;

    public AuthenticateUserController(LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserService.AuthInput input) {
        PagingPayload.PagingPayloadBuilder<LoginUserService.AuthOutput> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(loginUserService.login(input));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }

}
