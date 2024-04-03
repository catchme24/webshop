package com.example.security;

import com.example.dto.CustomerDto;
import com.example.entity.Customer;
import com.example.mapper.CustomerMapper;
import com.example.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Finding details of customer with login={}", username);
        Optional<Customer> findedCustomer = customerRepository.findByUsername(username);
        if (findedCustomer.isEmpty()) {
            log.warn("Customer with login={} not found", username);
            throw new UsernameNotFoundException("Customer with login=" + username + " not found");
        }
        CustomerDto customerDto = customerMapper.toDto(findedCustomer.get());
        log.debug("Founded users details: {}", customerDto);
        return customerDto;
    }
}
