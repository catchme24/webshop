package com.example.service;

import com.example.dto.ProductQuantityDto;
import com.example.dto.CustomerDto;
import com.example.dto.OrderDto;
import com.example.entity.*;
import com.example.mapper.OrderMapper;
import com.example.repository.CustomerRepository;
import com.example.repository.OrderProductRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.example.service.response.ServiceMessage;
import com.example.service.response.ServiceResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class OrderService implements ResponseProducer {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    private final OrderMapper orderMapper;


    public ServiceResponse<OrderDto> readAll(UserDetails userDetails) {
        CustomerDto user = (CustomerDto) userDetails;
        Optional<Customer> customer = customerRepository.findById(user.getCustomerId());
        List<OrderDto> orders = customer.get().getOrders().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
        return goodResponse(HttpStatus.OK, orders);
    }

    @Transactional
    public ServiceResponse<OrderDto> addProductInOrder(Integer orderId, ProductQuantityDto dto, UserDetails userDetails) {
        Optional<Order> order = orderRepository.findById(orderId);
        OrderProduct existingOrderProduct = order.get().getOrdersProducts().get(dto.getProductId());
        if (existingOrderProduct != null) {
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.ADDED_PRODUCT_ALREADY_IN_ORDER.name());
        }
        Optional<Product> product = productRepository.findById(dto.getProductId());

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder(order.get());
        orderProduct.setProduct(product.get());
        orderProduct.setQuantity(dto.getQuantity());

        order.get().getOrdersProducts().put(product.get().getProductId(), orderProduct);

        OrderDto saved = orderMapper.toDto(orderRepository.save(order.get()));
        return goodResponse(HttpStatus.ACCEPTED, saved);
    }

    @Transactional
    public ServiceResponse<OrderDto> changeCountOfProduct(Integer orderId, ProductQuantityDto dto, UserDetails userDetails) {
        Optional<Order> order = orderRepository.findById(orderId);
        order.get().getOrdersProducts().get(dto.getProductId()).setQuantity(dto.getQuantity());
        OrderDto saved = orderMapper.toDto(orderRepository.save(order.get()));
        return goodResponse(HttpStatus.ACCEPTED, saved);
    }

    @Transactional
    public ServiceResponse<OrderDto> formCurrentOrder(String paymentMethod, UserDetails userDetails) {
        CustomerDto user = (CustomerDto) userDetails;
        Optional<Customer> customer = customerRepository.findById(user.getCustomerId());
        Order first = customer.get().getOrders().stream()
                .filter(order -> order.getDeliveryList() == null)
                .findFirst().get();

        DeliveryList deliveryList = new DeliveryList();
        deliveryList.setDateArrived(LocalDate.now());
        deliveryList.setPaymentMethod(paymentMethod);
        deliveryList.setOrder(first);
        first.setDeliveryList(deliveryList);

        OrderDto order = orderMapper.toDto(orderRepository.save(first));
        return goodResponse(HttpStatus.OK, order);
    }
}
