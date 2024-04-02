package com.example.mapper.old;

import java.lang.reflect.Field;
import java.util.*;

public interface MapperSecond <E, D> {
    E toEntity(D dto);

    D toDto(E entity);

    D toDto(E entity, D dto);


}
