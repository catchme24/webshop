package com.example.mapper.old;

public interface Mapper2<F, T> {
    T map(F object);

    default T map(F fromObject, T toObject) {
        return toObject;
    }
}
