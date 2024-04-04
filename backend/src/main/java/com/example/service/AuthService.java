package com.example.service;

import com.example.dto.CustomerDto;
import com.example.dto.SuccesLoginDto;
import com.example.entity.Customer;
import com.example.security.JwtService;
import com.example.service.response.ServiceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements ResponseProducer {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final CustomerService customerService;

    public ServiceResponse<CustomerDto> registration(CustomerDto customerDto) {
        log.debug("Start registration user: {}", customerDto);
        ServiceResponse<CustomerDto> sr = customerService.create(customerDto);
        log.debug("End registration user: {}", customerDto);
        return sr;
    }

    @Transactional
    public ServiceResponse<SuccesLoginDto> login(CustomerDto customerDto) {
        log.debug("Start authenticating user: {}", customerDto);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        customerDto.getUsername(),
                        customerDto.getPassword()
                )
        );
        UserDetails user = userDetailsService.loadUserByUsername(customerDto.getUsername());
        CustomerDto currentUser = (CustomerDto) user;
        String jwtToken = jwtService.generateToken(user);
        SuccesLoginDto succesLoginDto = SuccesLoginDto
                        .builder()
                        .currentOrderId(null)
                        .address(currentUser.getCity())
                        .role(currentUser.getRole().name())
                        .userId(currentUser.getCustomerId().toString())
                        .token(jwtToken)
                        .build();
        log.debug("End authenticating user: {}", customerDto);
        return goodResponse(HttpStatus.ACCEPTED, succesLoginDto);
    }
}
