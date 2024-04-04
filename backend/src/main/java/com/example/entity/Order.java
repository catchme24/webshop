package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString(exclude = {"deliveryList", "customer"})
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


    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private DeliveryList deliveryList;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @MapKeyColumn(name = "product_id")
    private Map<Integer, OrderProduct> ordersProducts = new HashMap<>();
}

