package com.example.service;

import com.example.config.JwtService;
import com.example.dto.LoginDto;
import com.example.dto.LoginInfo;
import com.example.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public LoginInfo authenticate(LoginDto dto) {
        System.out.println("Try entry");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );
        System.out.println("half entry");

        UserDetails user = userDetailsService.loadUserByUsername(dto.getUsername());
        Customer customer = (Customer) user;
        String jwtToken = jwtService.generateToken(user);
        String address = "г. " + customer.getCity() + ", ул. " + customer.getStreet() + ", д. "
                + customer.getHouse() + ", кв. " + customer.getApartment();
        LoginInfo loginInfo = new LoginInfo(
        customer.getCustomerId(),
        customer.getOrders().stream()
                .filter(order -> order.getDeliveryList() == null)
                .findFirst()
                .get().getOrderId(),
        address,
        customer.getRole().name(),
        jwtToken
        );
        System.out.println("Entry");
        return loginInfo;
    }
}
