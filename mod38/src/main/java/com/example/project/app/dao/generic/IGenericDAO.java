package com.example.project.app.dao.generic;

import java.io.Serializable;
import java.util.Collection;

import com.example.project.app.domain.Persistente;
import com.example.project.app.exceptions.DAOException;
import com.example.project.app.exceptions.MaisDeUmRegistroException;
import com.example.project.app.exceptions.TableException;
import com.example.project.app.exceptions.TipoChaveNaoEncontradaException;

public interface IGenericDAO <T extends Persistente, E extends Serializable> {

    public T cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException;

    public void excluir(T entity) throws DAOException;

    public T alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException;

    public T consultar(E id) throws MaisDeUmRegistroException, TableException, DAOException;

    public Collection<T> buscarTodos() throws DAOException;
}
