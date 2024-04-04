package com.example.service;

import com.example.dto.CustomerDto;
import com.example.dto.DeliveryListDto;
import com.example.entity.DeliveryList;
import com.example.mapper.DeliveryListMapper;
import com.example.repository.DeliveryListRepository;
import com.example.service.response.ServiceMessage;
import com.example.service.response.ServiceResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DeliveryListService implements ResponseProducer {

    private final DeliveryListRepository deliveryListRepository;
    private final DeliveryListMapper deliveryListMapper;

    public ServiceResponse<DeliveryListDto> read(Long deliveryListId, UserDetails userDetails){
        CustomerDto user = (CustomerDto) userDetails;
        log.debug("Start finding delivery list with id={}", deliveryListId);
        Optional<DeliveryList> existingDeliveryList = deliveryListRepository.findById(deliveryListId.intValue());
        if (existingDeliveryList.isEmpty()) {
            log.warn("Сannot find delivery list with id={}, message: {}",
                    deliveryListId,
                    ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        if (!existingDeliveryList.get().getOrder().getCustomer().getCustomerId().equals(user.getCustomerId())) {
            log.warn("Сannot find delivery list with id={}, message: {}",
                                deliveryListId,
                                ServiceMessage.DELIVERYLIST_OWNER_ID_NOT_EQUALS_TO_AUTHORIZED_USER_ID.name());
            return errorResponse(HttpStatus.BAD_REQUEST,
                                ServiceMessage.DELIVERYLIST_OWNER_ID_NOT_EQUALS_TO_AUTHORIZED_USER_ID.name());
        }
        DeliveryListDto deliveryListDto = deliveryListMapper.toDto(existingDeliveryList.get());
        log.debug("End finding delivery list with id={}", deliveryListId);
        return goodResponse(HttpStatus.OK, deliveryListDto);
    }
}
