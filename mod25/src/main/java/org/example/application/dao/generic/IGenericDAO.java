package org.example.application.dao.generic;

import org.example.application.dao.interfaces.Persistente;

import java.io.Serializable;
import java.util.Collection;

public interface IGenericDAO <T extends Persistente, E extends Serializable> {
    Boolean cadastrar(T entity);
    T consultar(E key);
    void atualizar(T entity);
    void excluir(E key);
    Collection<T> buscarTodos();
}
