package com.example.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public abstract class AbstractDto implements Serializable {

//    private Long id;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
//    LocalDateTime created;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
//    LocalDateTime updated;
//    LocalDateTime created = LocalDateTime.now();
//    LocalDateTime updated = LocalDateTime.now();
}