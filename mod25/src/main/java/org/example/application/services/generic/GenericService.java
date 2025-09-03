package org.example.application.services.generic;

import org.example.application.dao.generic.IGenericDAO;
import org.example.application.dao.interfaces.Persistente;

import java.io.Serializable;
import java.util.Collection;

public abstract class GenericService <T extends Persistente, E extends Serializable> implements IGenericService<T, E> {

    protected IGenericDAO<T,E> dao;

    public GenericService(IGenericDAO<T,E> dao) {
        this.dao = dao;
    }

    @Override
    public Boolean cadastrar(T entity) {
        return dao.cadastrar(entity);
    }

    @Override
    public T consultar(E key) {
        return dao.consultar(key);
    }

    @Override
    public void atualizar(T entity) {
        dao.atualizar(entity);
    }

    @Override
    public void excluir(E key) {
        dao.excluir(key);
    }

    @Override
    public Collection<T> buscarTodos() {
        return dao.buscarTodos();
    }
}
