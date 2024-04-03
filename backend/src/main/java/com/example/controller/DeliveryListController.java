package com.example.controller;

import com.example.controller.util.ControllerUtils;
import com.example.dto.DeliveryListDto;
import com.example.service.DeliveryListService;
import com.example.service.response.ServiceResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/api/deliveryLists")
@RestController
@AllArgsConstructor
public class DeliveryListController {

    private final DeliveryListService deliveryListService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable("id") Long id,
                                  @AuthenticationPrincipal UserDetails userDetails) {

        ServiceResponse<DeliveryListDto> sr = deliveryListService.read(id, userDetails);
        return ControllerUtils.mapServiceResponseToHttpResponse(sr);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<?> readAll(@RequestBody Collection<Long> ids,
                                     @AuthenticationPrincipal UserDetails userDetails) {

        ServiceResponse<DeliveryListDto> sr = deliveryListService.readAll(ids, userDetails);
        return ControllerUtils.mapServiceResponseToHttpResponse(sr);
    }

}
