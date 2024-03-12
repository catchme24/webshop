package com.example.service;

import com.example.dto.DeliveryListCreateEditDto;
import com.example.dto.DeliveryListReadDto;
import com.example.entity.DeliveryList;
import com.example.mapper.DeliveryListCreateEditMapper;
import com.example.mapper.DeliveryListReadMapper;
import com.example.repository.DeliveryListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeliveryListService {

    private final DeliveryListRepository deliveryListRepository;
    private final DeliveryListReadMapper deliveryListReadMapper;
    private final DeliveryListCreateEditMapper deliveryListCreateEditMapper;

    public DeliveryListReadDto create(DeliveryListCreateEditDto dto){
        return deliveryListReadMapper.map(deliveryListRepository.save(deliveryListCreateEditMapper.map(dto)));
    }

    public DeliveryListReadDto read(Integer id){
        return deliveryListReadMapper.map(deliveryListRepository.findById(id).get());
    }

    public DeliveryListReadDto update(DeliveryListCreateEditDto dto, Integer id){
        DeliveryList deliveryList = deliveryListRepository.findById(id).get();
        return deliveryListReadMapper.map(deliveryListCreateEditMapper.map(dto, deliveryList));
    }

    public void delete(Integer id){
        deliveryListRepository.delete(deliveryListRepository.findById(id).get());
    }
}
