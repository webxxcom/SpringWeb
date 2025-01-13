package com.web.warehouse.sys.db.abstr;

import com.web.warehouse.sys.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface EntityDAO<T extends Entity> extends Processable<T> {
    /* Getters */
    Optional<T> getById(long id);
    List<T> getByName(String name);
    List<T> getByNameLike(String pattern);
    List<T> getAll();

    /* Updaters */
    void addAll(T[] entities);
    void add(T entity);
    void remove(long id);
    void update(T updated);
}
