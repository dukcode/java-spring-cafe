package com.kakao.cafe.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, V> {

    List<T> findAll();
    Optional<T> save(T obj);
    Optional<T> findOne(V primaryKey);
}