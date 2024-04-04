package com.example.mapper;

import com.example.dto.DeliveryListDto;
import com.example.dto.OrderDto;
import com.example.dto.ProductQuantityDto;
import com.example.entity.Order;
import com.example.repository.OrderRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
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
    private final DeliveryListMapper deliveryListMapper;

    @Autowired
    public OrderMapper(ModelMapper mapper, OrderRepository orderRepository, DeliveryListMapper deliveryListMapper) {
        super(Order.class, OrderDto.class);
        this.mapper = mapper;
        this.orderRepository = orderRepository;
        this.deliveryListMapper = deliveryListMapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Order.class, OrderDto.class)
                .addMappings(m -> {
                    m.skip(OrderDto::setCustomerId);
                    m.skip(OrderDto::setProductsWithQuantity);
                    m.skip(OrderDto::setDeliveryList);
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
        destination.setDeliveryList(getDeliveryList(source));
        destination.setProductsWithQuantity(getProductIds(source));
    }

    @Override
    public void mapSpecificFields(OrderDto source, Order destination) {

    }


    private Long getCustomerId(Order source) {
        return Objects.isNull(source) || Objects.isNull(source.getCustomer()) ?
                null :
                source.getCustomer().getCustomerId().longValue();
    }

    private DeliveryListDto getDeliveryList(Order source) {
        return Objects.isNull(source) || Objects.isNull(source.getDeliveryList()) ?
                null :
                deliveryListMapper.toDto(source.getDeliveryList());
    }

    private List<ProductQuantityDto> getProductIds(Order source) {
        return Objects.isNull(source) || source.getOrdersProducts().isEmpty() ? null :
                source.getOrdersProducts().values().stream()
                        .map(orderProduct ->
                                new ProductQuantityDto(orderProduct.getProduct().getProductId().intValue(),
                                                                orderProduct.getQuantity()))
                        .collect(Collectors.toList());
    }

}
