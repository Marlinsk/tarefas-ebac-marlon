package org.example.application.services.generic;

import org.example.application.dao.interfaces.Persistente;

import java.io.Serializable;
import java.util.Collection;

public interface IGenericService <T extends Persistente, E extends Serializable> {
    Boolean cadastrar(T entity);
    T consultar(E key);
    void atualizar(T entity);
    void excluir(E key);
    Collection<T> buscarTodos();
}
