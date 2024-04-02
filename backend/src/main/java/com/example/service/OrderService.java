package com.example.service;

import com.example.dto.*;
import com.example.dto.order.DeliveryListDto;
import com.example.dto.order.OrderDto;
import com.example.entity.DeliveryList;
import com.example.entity.Order;
import com.example.entity.OrderProduct;
import com.example.entity.Product;
import com.example.mapper.DeliveryListMapper;
import com.example.mapper.OrderMapper;
import com.example.mapper.old.DeliveryListCreateEditMapper2;
import com.example.mapper.old.OrderCreateEditMapper2;
import com.example.mapper.old.OrderReadMapper2;
import com.example.repository.DeliveryListRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final DeliveryListRepository deliveryListRepository;

    private final OrderMapper orderMapper;
    private final DeliveryListMapper deliveryListMapper;


    @Transactional
    public OrderDto getById(Integer id) {
        Order order = orderRepository.findById(id).get();
        System.out.println(order.getOrdersProducts().values().stream()
                .map(orderProduct -> orderProduct.getProduct())
                .collect(Collectors.toList()));
        OrderDto orderDto = orderMapper.toDto(order);
        return orderDto;
    }

    @Transactional
    public OrderDto create(OrderDto dto) {
        Order orderToBeSaved = orderMapper.toEntity(dto);
        OrderDto saved = orderMapper.toDto(orderRepository.save(orderToBeSaved));
        return saved;
    }

    @Transactional
    public void delete(Integer id) {
        orderRepository.delete(orderRepository.findById(id).get());
    }

    @Transactional
    public OrderDto addProductInOrder(Integer orderId, ProductQuantityDto dto) throws Exception {
        Optional<Order> order = orderRepository.findById(orderId);
        Optional<Product> product = productRepository.findById(dto.getProductId());
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder(order.get());
        orderProduct.setProduct(product.get());
        order.get().getOrdersProducts().put(product.get().getProductId(), orderProduct);

        OrderDto saved = orderMapper.toDto(orderRepository.save(order.get()));
        return saved;
    }

    @Transactional
    public OrderDto changeCountOfProduct(Integer orderId, ProductQuantityDto dto) {
        Optional<Order> order = orderRepository.findById(orderId);
        order.get().getOrdersProducts().get(dto.getProductId()).setQuantity(dto.getQuantity());

        OrderDto saved = orderMapper.toDto(orderRepository.save(order.get()));
        return saved;
    }

    @Transactional
    public void deleteProductFromOrder(Integer orderId, Integer productId) {

    }


    @Transactional
    public OrderDto addDeliveryList(Integer orderId, DeliveryListDto dto) {
        Optional<Order> order = orderRepository.findById(orderId);
        LocalDate dateGet = LocalDate.now();
        LocalDate dateArrived = LocalDate.from(dateGet).plusDays(3);
        DeliveryList deliveryList= deliveryListMapper.toEntity(dto);
        deliveryList.setOrder(order.get());
        deliveryList.setDateArrived(dateArrived);
        deliveryListRepository.save(deliveryList);
        order.map(entity -> {
            entity.setDeliveryList(deliveryList);
            return entity;
        });

        Order orderNew = new Order();
        orderNew.setCustomer(order.get().getCustomer());
        order.get().getCustomer().getOrders().add(orderNew);
        orderRepository.save(orderNew);
        return orderMapper.toDto(orderRepository.save(order.get()));
    }

}
