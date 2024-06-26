package com.example.service;

import com.example.dto.CustomerDto;
import com.example.dto.OrderDto;
import com.example.dto.ProductQuantityDto;
import com.example.entity.*;
import com.example.mapper.OrderMapper;
import com.example.repository.CustomerRepository;
import com.example.repository.OrderProductRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.example.service.response.ServiceMessage;
import com.example.service.response.ServiceResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderService implements ResponseProducer {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    private final OrderMapper orderMapper;


    public ServiceResponse<OrderDto> readAll(UserDetails userDetails) {
        CustomerDto user = (CustomerDto) userDetails;
        log.debug("Start finding all orders of user with id={}", user.getCustomerId());
        Optional<Customer> customer = customerRepository.findById(user.getCustomerId());
        List<OrderDto> orders = customer.get().getOrders().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
        log.debug("End finding all orders of user with id={}", user.getCustomerId());
        return goodResponse(HttpStatus.OK, orders);
    }

    @Transactional
    public ServiceResponse<OrderDto> addProductInOrder(Integer orderId, ProductQuantityDto dto, UserDetails userDetails) {
        CustomerDto user = (CustomerDto) userDetails;
        log.debug("Start adding product with id={} to order with id={}", dto.getProductId(), orderId);
        Optional<Product> existingProduct = productRepository.findById(dto.getProductId());
        if (existingProduct.isEmpty()) {
            log.warn("Сannot add product with id={}, message: {}", dto.getProductId(), ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        Optional<Order> existingOrder = orderRepository.findById(orderId);
        if (existingOrder.isEmpty()) {
            log.warn("Сannot find order with id={}", orderId);
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        if (!existingOrder.get().getCustomer().getCustomerId().equals(user.getCustomerId())) {
            log.warn("Сannot add product in order with id={} and owner id={}, message: {}",
                                orderId,
                                existingOrder.get().getCustomer().getCustomerId(),
                                ServiceMessage.ORDER_OWNER_ID_NOT_EQUALS_TO_AUTHORIZED_USER_ID.name());
            return errorResponse(HttpStatus.BAD_REQUEST,
                                ServiceMessage.ORDER_OWNER_ID_NOT_EQUALS_TO_AUTHORIZED_USER_ID.name());
        }
        OrderProduct existingOrderProduct = existingOrder.get().getOrdersProducts().get(dto.getProductId());
        if (existingOrderProduct != null) {
            log.warn("Сannot add product with id={}", dto.getProductId());
            return errorResponse(HttpStatus.BAD_REQUEST,
                                ServiceMessage.ADDED_PRODUCT_ALREADY_IN_ORDER.name());
        }
        Order order = existingOrder.get();
        Product product = existingProduct.get();

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setQuantity(dto.getQuantity());
        order.getOrdersProducts().put(product.getProductId(), orderProduct);
        OrderDto saved = orderMapper.toDto(orderRepository.save(order));
        log.debug("End adding product with id={} to order with id={}", dto.getProductId(), orderId);
        return goodResponse(HttpStatus.ACCEPTED, saved);
    }

    @Transactional
    public ServiceResponse<OrderDto> changeCountOfProduct(Integer orderId, ProductQuantityDto dto, UserDetails userDetails) {
        CustomerDto user = (CustomerDto) userDetails;
        log.debug("Start changing count of product with id={} in order with id={}", dto.getProductId(), orderId);
        Optional<Product> existingProduct = productRepository.findById(dto.getProductId());
        if (existingProduct.isEmpty()) {
            log.warn("Сannot change count of product with id={}, message: {}", dto.getProductId(), ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        Optional<Order> existingOrder = orderRepository.findById(orderId);
        if (existingOrder.isEmpty()) {
            log.warn("Сannot find order with id={}", orderId);
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        if (!existingOrder.get().getCustomer().getCustomerId().equals(user.getCustomerId())) {
            log.warn("Сannot change count of product in order with id={} and owner id={}, message: {}",
                    orderId,
                    existingOrder.get().getCustomer().getCustomerId(),
                    ServiceMessage.ORDER_OWNER_ID_NOT_EQUALS_TO_AUTHORIZED_USER_ID.name());
            return errorResponse(HttpStatus.BAD_REQUEST,
                    ServiceMessage.ORDER_OWNER_ID_NOT_EQUALS_TO_AUTHORIZED_USER_ID.name());
        }
        OrderProduct existingOrderProduct = existingOrder.get().getOrdersProducts().get(dto.getProductId());
        if (existingOrderProduct == null) {
            log.warn("Сannot change count of product with id={}", dto.getProductId());
            return errorResponse(HttpStatus.BAD_REQUEST,
                    ServiceMessage.PRODUCT_IS_NOT_IN_ORDER.name());
        }
        if (dto.getQuantity() <= 0) {
            existingOrder.get().getOrdersProducts().remove(dto.getProductId());
            orderProductRepository.delete(existingOrderProduct);
        } else {
            existingOrderProduct.setQuantity(dto.getQuantity());
        }
        OrderDto saved = orderMapper.toDto(orderRepository.save(existingOrder.get()));
        log.debug("End changing count of product with id={} to order with id={}", dto.getProductId(), orderId);
        return goodResponse(HttpStatus.ACCEPTED, saved);
    }

    @Transactional
    public ServiceResponse<OrderDto> formCurrentOrder(String paymentMethod, UserDetails userDetails) {
        CustomerDto user = (CustomerDto) userDetails;
        log.debug("Start form current order for user with id={}", user.getCustomerId());
        Optional<Customer> customer = customerRepository.findById(user.getCustomerId());
        Order currentOrder = customer.get().getOrders().stream()
                .filter(order -> order.getDeliveryList() == null)
                .findFirst().get();

        if (currentOrder.getOrdersProducts().isEmpty()) {
            log.warn("Сannot form order with id={}", currentOrder.getOrderId());
            return errorResponse(HttpStatus.BAD_REQUEST,ServiceMessage.ORDER_SHOULD_NOT_BE_EMPTY.name());
        }

        DeliveryList deliveryList = new DeliveryList();
        deliveryList.setDateArrived(LocalDate.now());
        deliveryList.setPaymentMethod(paymentMethod);
        deliveryList.setOrder(currentOrder);
        currentOrder.setDeliveryList(deliveryList);
        currentOrder.setDateGet(LocalDate.now().plusDays(3L));
        OrderDto order = orderMapper.toDto(orderRepository.save(currentOrder));

        //Create new empty order
        Order emptyOrder = new Order();
        emptyOrder.setCustomer(customer.get());
        orderRepository.save(emptyOrder);
        log.debug("End form current order for user with id={}", user.getCustomerId());
        return goodResponse(HttpStatus.OK, order);
    }
}
