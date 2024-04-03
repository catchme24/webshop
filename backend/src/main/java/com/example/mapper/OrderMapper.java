package com.example.mapper;

import com.example.dto.OrderDto;
import com.example.entity.Order;
import com.example.repository.OrderRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class OrderMapper extends AbstractMapper<Order, OrderDto> {

    private final ModelMapper mapper;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderMapper(ModelMapper mapper, OrderRepository orderRepository) {
        super(Order.class, OrderDto.class);
        this.mapper = mapper;
        this.orderRepository = orderRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Order.class, OrderDto.class)
                .addMappings(m -> {
                    m.skip(OrderDto::setCustomerId);
                    m.skip(OrderDto::setProductsId);
                    m.skip(OrderDto::setDeliveryListId);
                }).setPostConverter(toDtoConverter());

        mapper.createTypeMap(OrderDto.class, Order.class)
                .addMappings(m -> {
                    m.skip(Order::setCustomer);
                    m.skip(Order::setDeliveryList);
                    m.skip(Order::setOrdersProducts);
                }).setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(Order source, OrderDto destination) {
        destination.setCustomerId(getCustomerId(source));
        destination.setDeliveryListId(getDeliveryListId(source));
        destination.setProductsId(getProductIds(source));
    }

    @Override
    public void mapSpecificFields(OrderDto source, Order destination) {

    }


    private Long getCustomerId(Order source) {
        return Objects.isNull(source) || Objects.isNull(source.getCustomer()) ? null : source.getCustomer().getCustomerId().longValue();
    }

    private Long getDeliveryListId(Order source) {
        return Objects.isNull(source) || Objects.isNull(source.getDeliveryList()) ? null : source.getDeliveryList().getDeliveryId().longValue();
    }

    private List<Long> getProductIds(Order source) {
        return Objects.isNull(source) || source.getOrdersProducts().isEmpty() ? null :
                source.getOrdersProducts().values().stream()
                        .map(orderProduct -> orderProduct.getProduct().getProductId().longValue())
                        .collect(Collectors.toList());
    }

}
