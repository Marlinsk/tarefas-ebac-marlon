package main.java.com.projeto1.dao;

import java.util.List;

public interface IGenericDao<T, ID> {
    T salvar(T entity);
    List<T> listarTodos() ;
    boolean removerPorId(ID id);
}
