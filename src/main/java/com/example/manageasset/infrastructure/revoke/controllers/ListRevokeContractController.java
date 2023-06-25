package com.example.manageasset.infrastructure.revoke.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/revoke-contract")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ListRevokeContractController {
}
