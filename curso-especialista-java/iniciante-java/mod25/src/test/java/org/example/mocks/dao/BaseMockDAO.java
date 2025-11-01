package org.example.mocks.dao;

import org.example.application.dao.generic.IGenericDAO;
import org.example.application.dao.interfaces.Persistente;

import java.io.Serializable;
import java.util.*;

public abstract class BaseMockDAO<T extends Persistente, E extends Serializable> implements IGenericDAO<T, E> {

    protected final Map<E, T> store = new LinkedHashMap<>();

    @Override
    public Boolean cadastrar(T entity) {
        E key = getKey(entity);
        Objects.requireNonNull(key, "Chave não pode ser nula");
        if (store.containsKey(key)) return false;
        store.put(key, entity);
        return true;
    }

    @Override
    public T consultar(E key) {
        return store.get(key);
    }

    @Override
    public void atualizar(T entity) {
        E key = getKey(entity);
        if (!store.containsKey(key)) {
            throw new IllegalStateException("Entidade não encontrada para atualizar: " + key);
        }
        store.put(key, entity);
    }

    @Override
    public void excluir(E key) {
        store.remove(key);
    }

    @Override
    public Collection<T> buscarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(store.values()));
    }

    protected abstract E getKey(T entity);

    public void clear() {
        store.clear();
    }

    public int size() {
        return store.size();
    }

    public Set<E> keys() {
        return new LinkedHashSet<>(store.keySet());
    }
}
