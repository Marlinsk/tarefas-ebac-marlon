package org.example.application.dao.mapper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SingletonMap {

    private static SingletonMap singletonMap;

    protected Map<Class<?>, Map<?, ?>> map;

    private SingletonMap() {
        map = new HashMap<>();
    }

    public static synchronized SingletonMap getInstance() {
        if (singletonMap == null) {
            singletonMap = new SingletonMap();
        }
        return singletonMap;
    }

    public Map<Class<?>, Map<?, ?>> getMap() {
        return this.map;
    }

    @SuppressWarnings("unchecked")
    public <K, V> Map<K, V> getTypedStore(Class<V> entityClass) {
        return (Map<K, V>) map.computeIfAbsent(entityClass, k -> new LinkedHashMap<K, V>());
    }

    public void clearAll() {
        map.values().forEach(Map::clear);
    }

    public <T> void clearForClass(Class<T> clazz) {
        Map<?, ?> inner = map.get(clazz);
        if (inner != null) {
            inner.clear();
        }
    }

    public <T> int countForClass(Class<T> clazz) {
        Map<?, ?> inner = map.get(clazz);
        return inner != null ? inner.size() : 0;
    }
}
