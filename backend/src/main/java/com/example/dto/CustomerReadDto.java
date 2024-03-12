package com.example.dto;

import com.example.entity.Role;
import lombok.Value;
import java.util.List;

@Value
public class CustomerReadDto {

    Integer customerId;

    String firstName;

    String lastName;

    String phoneNumber;

    String city;

    String street;

    Integer house;

    Integer apartment;

    String username;

    String password;

    Role role;

    List<OrderReadDto> orders;
}
