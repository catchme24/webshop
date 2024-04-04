package com.example.service;

import com.example.dto.CustomerDto;
import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.mapper.CustomerMapper;
import com.example.repository.CustomerRepository;
import com.example.service.response.ServiceMessage;
import com.example.service.response.ServiceResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CustomerService implements ResponseProducer {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ServiceResponse<CustomerDto> create(CustomerDto customerToBeAdded) {
        log.debug("Start adding new user={}", customerToBeAdded);
        if (customerToBeAdded.getCustomerId() != null) {
            log.warn("Сannot add new user: {}, message: {}", customerToBeAdded, ServiceMessage.SHOULD_NOT_HAS_ID.name());
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.SHOULD_NOT_HAS_ID.name());
        }
        Optional<Customer> existingCustomer = customerRepository.findByUsername(customerToBeAdded.getUsername());
        if (existingCustomer.isPresent()) {
            log.warn(   "Сannot add new user: {}, message: {}",
                        customerToBeAdded,
                        ServiceMessage.USER_WITH_THIS_USERNAME_ALREADY_EXIST.name());
            return errorResponse(HttpStatus.BAD_REQUEST,
                        ServiceMessage.USER_WITH_THIS_USERNAME_ALREADY_EXIST.name());
        }
        customerToBeAdded.setPassword(passwordEncoder.encode(customerToBeAdded.getPassword()));
        Customer customerToBeSaved = customerMapper.toEntity(customerToBeAdded);
        Order order = new Order();
        order.setCustomer(customerToBeSaved);
        customerToBeSaved.setOrders(List.of(order));
        CustomerDto customerDto = customerMapper.toDto(customerRepository.save(customerToBeSaved));
        log.debug("End adding new user={}", customerToBeAdded);
        return goodResponse(HttpStatus.CREATED, customerDto);
    }

}
