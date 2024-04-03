package com.example.mapper;

import com.example.dto.CustomerDto;
import com.example.entity.Customer;
import com.example.repository.CustomerRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomerMapper extends AbstractMapper<Customer, CustomerDto> {

    private final ModelMapper mapper;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerMapper(ModelMapper mapper, CustomerRepository customerRepository) {
        super(Customer.class, CustomerDto.class);
        this.mapper = mapper;
        this.customerRepository = customerRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Customer.class, CustomerDto.class)
                .addMappings(m -> {
                    m.skip(CustomerDto::setOrdersId);
                }).setPostConverter(toDtoConverter());

        mapper.createTypeMap(CustomerDto.class, Customer.class)
                .addMappings(m -> {
                    m.skip(Customer::setOrders);
                }).setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(Customer source, CustomerDto destination) {
        destination.setOrdersId(source.getOrders()
                .stream()
                .map(order -> order.getOrderId().longValue())
                .collect(Collectors.toList()));
    }

    @Override
    public void mapSpecificFields(CustomerDto source, Customer destination) {

    }

}
