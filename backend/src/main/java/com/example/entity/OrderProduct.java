package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "order_products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"product", "order"})
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Integer orderProductId;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
//    @JsonIgnore
    private Product product;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "order_id")
    private Order order;

}

