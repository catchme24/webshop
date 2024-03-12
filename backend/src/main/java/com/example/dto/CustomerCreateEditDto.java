package com.example.dto;

import lombok.Value;

import java.util.List;

@Value
public class CustomerCreateEditDto {

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

    List<Integer> orderIds;
}
