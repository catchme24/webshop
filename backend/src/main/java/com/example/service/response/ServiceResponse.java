package com.example.service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ServiceResponse<D> {

    private HttpStatus httpStatus;
    private String errorMessage;
    private List<D> content;

}
