package com.example.service;

import com.example.service.response.ServiceResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public interface ApiService<D> extends ResponseProducer {

    ServiceResponse<D> readAll(UserDetails principal);

    ServiceResponse<D> read(Long id, UserDetails principal);

    ServiceResponse<D> create(D dto, UserDetails principal);

    ServiceResponse<D> update(D dto, UserDetails principal);

    ServiceResponse<D> delete(Long id, UserDetails principal);
}
