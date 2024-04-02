package com.example.service;

import com.example.dto.order.CustomerDto;
import com.example.security.JwtService;
import com.example.dto.LoginDto;
import com.example.dto.LoginInfo;
import com.example.entity.Customer;
import com.example.service.response.ServiceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

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
    public ServiceResponse<String> login(CustomerDto customerDto) {
        System.out.println("Try entry");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        customerDto.getUsername(),
                        customerDto.getPassword()
                )
        );
        System.out.println("half entry");

        UserDetails user = userDetailsService.loadUserByUsername(customerDto.getUsername());
//        Customer customer = (Customer) user;
        String jwtToken = jwtService.generateToken(user);
//        String address = "г. " + customer.getCity() + ", ул. " + customer.getStreet() + ", д. "
//                + customer.getHouse() + ", кв. " + customer.getApartment();
//        LoginInfo loginInfo = new LoginInfo(
//        customer.getCustomerId(),
//        customer.getOrders().stream()
//                .filter(order -> order.getDeliveryList() == null)
//                .findFirst()
//                .get().getOrderId(),
//        address,
//        customer.getRole().name(),
//        jwtToken
//        );
        System.out.println("Entry");
        return goodResponse(null, jwtToken);
    }
}
