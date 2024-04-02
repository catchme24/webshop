package com.example.service;

import com.example.dto.order.CustomerDto;
import com.example.mapper.CustomerMapper;
import com.example.repository.CustomerRepository;
import com.example.service.response.ServiceResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CustomerService implements ResponseProducer {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional
    public ServiceResponse<CustomerDto> create(CustomerDto dto) {
        CustomerDto customerDto = customerMapper.toDto(customerRepository.save(customerMapper.toEntity(dto)));
        return goodResponse(null, customerDto);
    }

}
