package com.example.mapper.old;

import com.example.dto.OrderProductReadDto;
import com.example.entity.OrderProduct;
import com.example.mapper.old.Mapper2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProductReadMapper2 implements Mapper2<OrderProduct, OrderProductReadDto> {

    @Override
    public OrderProductReadDto map(OrderProduct object) {
        return new OrderProductReadDto(
                object.getOrderProductId(),
                object.getQuantity());
    }
}
