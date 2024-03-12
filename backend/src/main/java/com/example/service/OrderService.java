package com.example.service;

import com.example.dto.DeliveryListCreateEditDto;
import com.example.dto.OrderCreateEditDto;
import com.example.dto.OrderReadDto;
import com.example.dto.ProductQuantityCreateEditDto;
import com.example.entity.DeliveryList;
import com.example.entity.Order;
import com.example.entity.OrderProduct;
import com.example.entity.Product;
import com.example.mapper.DeliveryListCreateEditMapper;
import com.example.mapper.OrderCreateEditMapper;
import com.example.mapper.OrderReadMapper;
import com.example.repository.DeliveryListRepository;
import com.example.repository.OrderProductRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryListRepository deliveryListRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderReadMapper orderReadMapper;
    private final OrderCreateEditMapper orderCreateEditMapper;
    private final DeliveryListCreateEditMapper deliveryListCreateEditMapper;


    public OrderReadDto create(OrderCreateEditDto dto){
        return orderReadMapper.map(orderRepository.save(orderCreateEditMapper.map(dto)));
    }

//    public OrderReadDto read(Integer id){
//        return orderReadMapper.map(orderRepository.findById(id).get());
//    }
//
//    public OrderReadDto update(OrderCreateEditDto dto, Integer id){
//        Order order = orderRepository.findById(id).get();
//        return orderReadMapper.map(orderCreateEditMapper.map(dto, order));
//    }

    public void delete(Integer id){
        orderRepository.delete(orderRepository.findById(id).get());
    }

    @Transactional
    public OrderReadDto addProductInOrder(Integer orderId, ProductQuantityCreateEditDto dto) throws Exception {
        Optional<Order> order = orderRepository.findById(orderId);
        List<OrderProduct> orderProductList = order.get().getOrderProducts();
        for(int i = 0; i < orderProductList.size(); i++){
            if(orderProductList.get(i).getProduct().getProductId() == dto.getProductId()){
                throw new Exception("e");
            }
        }
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder(order.get());
        Optional<Product> product = productRepository.findById(dto.getProductId());
        orderProduct.setProduct(product.get());
        orderProduct.setQuantity(dto.getQuantity());
        OrderProduct saved = orderProductRepository.save(orderProduct);
//        List<OrderProduct> orderProductList = order.get().getOrderProducts();
//        orderProductList.add(orderProduct);
//        orderRepository.save(order.get());
        System.out.println(saved.toString());
        return orderReadMapper.map(orderRepository.findById(orderId).get());
    }

    @Transactional
    public OrderReadDto changeCountOfProduct(Integer orderId, ProductQuantityCreateEditDto dto) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(entity -> {
            List<OrderProduct> orderProductList = entity.getOrderProducts();
            for (OrderProduct op: orderProductList) {
                if (op.getProduct().getProductId() == dto.getProductId()) {
                    op.setQuantity(dto.getQuantity());
                    orderProductRepository.save(op);
                    break;
                }
            }
            return orderReadMapper.map(entity);
        })
        .get();
    }

    @Transactional
    public void deleteProductFromOrder(Integer orderId, Integer productId) {
        Optional<Order> order = orderRepository.findById(orderId);
            order.map(entity -> {
                    List<OrderProduct> orderProductList = entity.getOrderProducts();
                    for (OrderProduct op: orderProductList) {
                        if (op.getProduct().getProductId() == productId) {
                            orderProductRepository.delete(op);
                            break;
                        }
                    }
                    return orderReadMapper.map(entity);
                })
                .get();
    }

//    @Transactional
//    public OrderReadDto addDeliveryList(Integer orderId, DeliveryListCreateEditDto dto) {
//        Optional<Order> order = orderRepository.findById(orderId);
//        order.map(entity -> {
//            entity.setDeliveryList(deliveryListCreateEditMapper.map(dto));
//            return entity;
//        });
//        return orderReadMapper.map(orderRepository.save(order.get()));
//    }
    @Transactional
    public OrderReadDto addDeliveryList(Integer orderId, DeliveryListCreateEditDto dto) {
        Optional<Order> order = orderRepository.findById(orderId);
        LocalDate dateGet = LocalDate.now();
        LocalDate dateArrived = LocalDate.from(dateGet).plusDays(3);
        order.get().setDateGet(dateGet);
        DeliveryList deliveryList= deliveryListCreateEditMapper.map(dto);
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
        return orderReadMapper.map(orderRepository.save(order.get()));
    }


}
