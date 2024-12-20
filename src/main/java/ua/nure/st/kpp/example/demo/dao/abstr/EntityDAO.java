package ua.nure.st.kpp.example.demo.dao.abstr;

import ua.nure.st.kpp.example.demo.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface EntityDAO<T extends Entity> extends Processable<T> {
    /* Getters */
    Optional<T> getById(long id);
    List<T> getByName(String name);
    List<T> getByNameLike(String pattern);
    List<T> getAll();

    /* Updaters */
    void insertAll(T[] entities);
    void insert(T entity);
    void delete(long id);
}
