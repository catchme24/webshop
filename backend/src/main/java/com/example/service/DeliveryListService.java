package com.example.service;

import com.example.dto.order.DeliveryListDto;
import com.example.dto.order.ProductDto;
import com.example.mapper.DeliveryListMapper;
import com.example.repository.DeliveryListRepository;
import com.example.service.response.ServiceResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class DeliveryListService implements ResponseProducer {

    private final DeliveryListRepository deliveryListRepository;
    private final DeliveryListMapper deliveryListMapper;

    public ServiceResponse<DeliveryListDto> read(Long deliveryListId, UserDetails userDetails){
        DeliveryListDto deliveryListDto = deliveryListMapper.toDto(deliveryListRepository.findById(deliveryListId.intValue()).get());
        return goodResponse(null, deliveryListDto);
    }

    public ServiceResponse<DeliveryListDto> readAll(Collection<Long> ids, UserDetails userDetails) {

        List<DeliveryListDto> deliveryLists = deliveryListRepository.findAllById(ids
                        .stream()
                        .mapToInt(x -> x.intValue())
                        .boxed()
                        .collect(Collectors.toList()))
                .stream()
                .map(deliveryListMapper::toDto)
                .collect(Collectors.toList());
        return goodResponse(null, deliveryLists);
    }
}
