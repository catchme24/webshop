package com.example.mapper.old;

import com.example.dto.DeliveryListReadDto;
import com.example.entity.DeliveryList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryListReadMapper2 implements Mapper2<DeliveryList, DeliveryListReadDto> {

    @Override
    public DeliveryListReadDto map(DeliveryList object) {

        return new DeliveryListReadDto(
                object.getDeliveryId(),
                object.getDateArrived(),
                object.getPaymentMethod());
    }
}
