package com.web.warehouse.sys.service;

import java.util.List;
import java.util.Optional;

public interface EntityService<T> {
    Optional<T> get(long id);

    List<T> getAll();

    void add(T entity);

    void addAll(T[] entity);

    void remove(long id);

    void update(T updated);
}
