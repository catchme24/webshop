package com.example.controller;


import com.example.dto.CustomerCreateEditDto;
import com.example.dto.CustomerReadDto;
import com.example.dto.LoginDto;
import com.example.dto.LoginInfo;
import com.example.service.AuthService;
import com.example.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final CustomerService customerService;
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody CustomerCreateEditDto dto) throws Exception{
        System.out.println(dto.toString());
        CustomerReadDto customerReadDto = customerService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        LoginInfo loginInfo = authService.authenticate(dto);
        return ResponseEntity
                .ok()
                .body(loginInfo);
    }
}
