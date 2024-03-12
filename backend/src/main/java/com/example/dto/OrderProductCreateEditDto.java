package com.example.dto;

import lombok.Value;

@Value
public class OrderProductCreateEditDto {

    Integer quantity;

    Integer productId;

    Integer orderId;
}


//1) Чел с фронта кидает нам {
//        Integer quantity;
//        Integer productId;
//        Integer orderId;
//        }
//
//2) Мы тут на бекенде создаем
//        OrderProduct op = new OrderProduct();
//
//        getProduct(productId);
//        Order order = getOrder(orderId);
//
//        op.setProduct();
//        op.setOrder();
//
//        order.setOrderProduct(op);
//        repository.save(order);
//
