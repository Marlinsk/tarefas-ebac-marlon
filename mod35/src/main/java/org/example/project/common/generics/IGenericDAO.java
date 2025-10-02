package org.example.project.common.generics;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IGenericDAO<T, ID extends Serializable> {
    T save(T entity);
    T update(T entity);
    Optional<T> findById(ID id);
    boolean exists(ID id);
    List<T> findAll();
    void deleteById(ID id);
}
