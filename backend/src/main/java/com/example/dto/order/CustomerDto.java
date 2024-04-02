package com.example.dto.order;

import com.example.entity.Order;
import com.example.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto extends AbstractDto {


    private Integer customerId;


    private String firstName;


    private String lastName;


    private String phoneNumber;


    private String city;

    private String street;


    private Integer house;

    private Integer apartment;

    private String username;


    private String password;

    private Role role;

    private List<Long> ordersId;
}
