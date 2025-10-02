package org.example.project.repositories;

import java.util.List;

public interface ICrudRepository<T, ID> {
    T salvar(T entity);
    T atualizar(T entity);
    T buscarPorId(ID id);
    List<T> listarTodos();
    void remover(T entity);
    long contar();
}
