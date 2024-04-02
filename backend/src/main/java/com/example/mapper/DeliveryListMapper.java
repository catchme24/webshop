package com.example.mapper;

import com.example.dto.order.DeliveryListDto;
import com.example.entity.DeliveryList;
import com.example.repository.DeliveryListRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeliveryListMapper extends AbstractMapper<DeliveryList, DeliveryListDto> {

    private final ModelMapper mapper;
    private final DeliveryListRepository deliveryListRepository;

    @Autowired
    public DeliveryListMapper(ModelMapper mapper, DeliveryListRepository deliveryListRepository) {
        super(DeliveryList.class, DeliveryListDto.class);
        this.mapper = mapper;
        this.deliveryListRepository = deliveryListRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(DeliveryList.class, DeliveryListDto.class)
                .addMappings(m -> {
                    m.skip(DeliveryListDto::setOrderId);
                }).setPostConverter(toDtoConverter());

        mapper.createTypeMap(DeliveryListDto.class, DeliveryList.class)
                .addMappings(m -> {
                    m.skip(DeliveryList::setOrder);
                }).setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(DeliveryList source, DeliveryListDto destination) {
        destination.setOrderId(source.getOrder().getOrderId().longValue());
    }

    @Override
    public void mapSpecificFields(DeliveryListDto source, DeliveryList destination) {

    }

}