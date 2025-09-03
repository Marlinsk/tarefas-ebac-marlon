package org.example.application.dao.generic;

import org.example.application.annotation.TipoChave;
import org.example.application.dao.interfaces.Persistente;
import org.example.application.dao.mapper.SingletonMap;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class GenericDAO<T extends Persistente, E extends Serializable> implements IGenericDAO<T, E> {

    private final Class<T> entityClass;
    private final Map<Class<?>, Map<?, ?>> database;

    public GenericDAO() {
        this.entityClass = resolveEntityClass();
        this.database = SingletonMap.getInstance().getMap();
        database.computeIfAbsent(entityClass, k -> new LinkedHashMap<E, T>());
    }

    @Override
    public Boolean cadastrar(T entity) {
        Objects.requireNonNull(entity, "Entidade não pode ser nula");
        Map<E, T> store = typedStore();
        E key = extractKey(entity);
        if (key == null) {
            throw new IllegalStateException(msgChaveAusente());
        }
        if (store.containsKey(key)) return false;
        store.put(key, entity);
        return true;
    }

    @Override
    public T consultar(E key) {
        Map<E, T> store = typedStore();
        return store.get(key);
    }

    @Override
    public void atualizar(T entity) {
        Objects.requireNonNull(entity, "Entidade não pode ser nula");
        Map<E, T> store = typedStore();
        E key = extractKey(entity);
        if (key == null) {
            throw new IllegalStateException(msgChaveAusente());
        }
        if (!store.containsKey(key)) {
            throw new IllegalStateException("Entidade com chave [" + key + "] não encontrada para atualizar");
        }
        store.put(key, entity);
    }

    @Override
    public void excluir(E key) {
        Map<E, T> store = typedStore();
        store.remove(key);
    }

    @Override
    public Collection<T> buscarTodos() {
        Map<E, T> store = typedStore();
        return Collections.unmodifiableList(new ArrayList<>(store.values()));
    }

    private Map<E, T> typedStore() {
        return (Map<E, T>) database.get(entityClass);
    }

    private String msgChaveAusente() {
        return "Não foi possível extrair a chave (@TipoChave) da entidade " + entityClass.getSimpleName();
    }

    private E extractKey(T entity) {
        Field[] fields = entityClass.getDeclaredFields();

        for (Field f : fields) {
            TipoChave ann = f.getAnnotation(TipoChave.class);

            if (ann != null) {
                String getterName = ann.value();
                try {
                    Method m = entityClass.getMethod(getterName);
                    Object value = m.invoke(entity);
                    return (E) value;
                } catch (NoSuchMethodException e) {
                    throw new IllegalStateException("Getter '" + getterName + "' não encontrado em " + entityClass.getName(), e);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    throw new IllegalStateException("Falha ao invocar getter '" + getterName + "' em " + entityClass.getName(), e);
                }
            }
        }

        return null;
    }

    private Class<T> resolveEntityClass() {
        Type type = getClass().getGenericSuperclass();

        while (type instanceof Class) {
            type = ((Class<?>) type).getGenericSuperclass();
        }

        if (type instanceof ParameterizedType) {
            Type t = ((ParameterizedType) type).getActualTypeArguments()[0];
            if (t instanceof Class) {
                return (Class<T>) t;
            }
        }

        throw new IllegalStateException("Não foi possível resolver o tipo da entidade (T) para " + getClass().getName());
    }
}
