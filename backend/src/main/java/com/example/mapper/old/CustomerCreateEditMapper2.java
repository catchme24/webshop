package com.example.mapper.old;

import com.example.dto.CustomerCreateEditDto;
import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.entity.Role;
import com.example.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class CustomerCreateEditMapper2 implements Mapper2<CustomerCreateEditDto, Customer> {

    private final OrderRepository orderRepository;
    @Override
    public Customer map(CustomerCreateEditDto fromObject, Customer toObject) {
        copy(fromObject, toObject);
        return toObject;
    }
    @Override
    public Customer map(CustomerCreateEditDto object) {
        Customer customer = new Customer();
        copy(object, customer);
        return customer;
    }

    private void copy (CustomerCreateEditDto fromObject, Customer toObject){
        toObject.setCustomerId(fromObject.getCustomerId());
        toObject.setFirstName(fromObject.getFirstName());
        toObject.setLastName(fromObject.getLastName());
        toObject.setPhoneNumber(fromObject.getPhoneNumber());
        toObject.setCity(fromObject.getCity());
        toObject.setStreet(fromObject.getStreet());
        toObject.setHouse(fromObject.getHouse());
        toObject.setApartment(fromObject.getApartment());
        toObject.setUsername(fromObject.getUsername());
        toObject.setPassword(fromObject.getPassword());
        toObject.setRole(Role.USER);
        if (fromObject.getOrderIds() == null){

        } else {
            toObject.setOrders(getOrders(fromObject.getOrderIds(), toObject.getOrders()));
        }
    }

    private List<Order> getOrders(List<Integer> orderIds, List<Order> orders){
        for(int i = 0; i < orderIds.size(); i++){
            Integer id = orderIds.get(i);
            orders.remove(i);
            Order order = Optional.ofNullable(id)
                .flatMap(orderRepository::findById)
                .orElse(null);
            orders.add(i, order);
        }
        return orders;
    }
}
