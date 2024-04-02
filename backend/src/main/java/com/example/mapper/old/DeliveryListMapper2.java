package com.example.mapper.old;

import com.example.dto.order.DeliveryListDto;
import com.example.entity.DeliveryList;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeliveryListMapper2 implements MapperSecond<DeliveryList, DeliveryListDto>{

//    private OrderMapper orderMapper;
    private ModelMapper modelMapper;
    @Override
    public DeliveryList toEntity(DeliveryListDto dto) {
        return null;
    }

    @Override
    public DeliveryListDto toDto(DeliveryList entity) {

        return null;
    }

    @Override
    public DeliveryListDto toDto(DeliveryList entity, DeliveryListDto dto) {
        TypeMap<DeliveryList, DeliveryListDto> propertyMapper = modelMapper.createTypeMap(DeliveryList.class, DeliveryListDto.class);
        propertyMapper.addMappings(mapper -> mapper.skip(DeliveryListDto::setOrderId));
        modelMapper.map(entity, dto);
        return dto;
    }


}
