package com.example.mapper;

import com.example.dto.DeliveryListReadDto;
import com.example.entity.DeliveryList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryListReadMapper implements Mapper<DeliveryList, DeliveryListReadDto>{

    @Override
    public DeliveryListReadDto map(DeliveryList object) {

        return new DeliveryListReadDto(
                object.getDeliveryId(),
                object.getDateArrived(),
                object.getPaymentMethod());
    }
}
