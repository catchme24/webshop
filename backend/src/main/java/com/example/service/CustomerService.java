package com.example.service;

import com.example.dto.CustomerDto;
import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.mapper.CustomerMapper;
import com.example.repository.CustomerRepository;
import com.example.service.response.ServiceResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CustomerService implements ResponseProducer {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ServiceResponse<CustomerDto> create(CustomerDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Customer customerToBeSaved = customerMapper.toEntity(dto);
        Order order = new Order();
        order.setCustomer(customerToBeSaved);

        customerToBeSaved.setOrders(List.of(order));
        CustomerDto customerDto = customerMapper.toDto(customerRepository.save(customerToBeSaved));

        return goodResponse(HttpStatus.CREATED, customerDto);
    }

}
