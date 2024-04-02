package com.example.mapper.old;

import com.example.dto.OrderReadDto;
import com.example.entity.Order;
import com.example.mapper.old.DeliveryListReadMapper2;
import com.example.mapper.old.Mapper2;
import com.example.mapper.old.ProductReadMapper2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderReadMapper2 implements Mapper2<Order, OrderReadDto> {

    private final ProductReadMapper2 productReadMapper;
    private final DeliveryListReadMapper2 deliveryListReadMapper;

    @Override
    public OrderReadDto map(Order object) {
//        List<ProductQuantity> products = object.getOrderProducts().stream()
//                .map(orderProduct -> {
//                    return new ProductQuantity(
//                      productReadMapper.map(orderProduct.getProduct()),
//                      orderProduct.getQuantity()
//                    );
//                })
//                .collect(Collectors.toList());
//        DeliveryListReadDto deliveryListReadDto;
//        if (object.getDeliveryList() != null){
//            deliveryListReadDto = deliveryListReadMapper.map(object.getDeliveryList());
//        } else {
//            deliveryListReadDto = null;
//        }
//        return new OrderReadDto(
//                object.getOrderId(),
//                object.getDateGet(),
//                deliveryListReadDto,
//                products);
//    }
                return new OrderReadDto(
                object.getOrderId(),
                null,
                null,
                null);
    }
}
