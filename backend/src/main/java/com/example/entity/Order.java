package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "date_get")
    private LocalDate dateGet;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @OneToOne(mappedBy = "order")
    private DeliveryList deliveryList;

    @OneToMany(mappedBy = "order")
//    @MapKey(name = "product_id")
    @MapKeyColumn(name = "product_id")
    private Map<Integer, OrderProduct> ordersProducts = new HashMap<>();
}

