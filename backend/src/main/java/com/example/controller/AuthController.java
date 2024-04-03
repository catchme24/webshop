package com.example.controller;


import com.example.controller.util.ControllerUtils;
import com.example.dto.CustomerDto;
import com.example.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomerDto customerDto) {
        return ControllerUtils.mapServiceResponseToHttpResponse(authService.login(customerDto));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody CustomerDto customerDto) {
        return ControllerUtils.mapServiceResponseToHttpResponse(authService.registration(customerDto));
    }
}
