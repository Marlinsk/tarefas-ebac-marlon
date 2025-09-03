package org.example.application.dao;

import org.example.application.dao.generic.GenericDAO;
import org.example.application.dao.interfaces.INotaDAO;
import org.example.application.dao.mapper.SingletonMap;
import org.example.application.domain.Nota;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class NotaDAO extends GenericDAO<Nota, String> implements INotaDAO {

    @Override
    public Nota buscarPorChaveAcesso(String chaveAcesso) {
        if (chaveAcesso == null) return null;
        return buscarTodos().stream().filter(n -> n.getChaveAcesso() != null && n.getChaveAcesso().equalsIgnoreCase(chaveAcesso)).findFirst().orElse(null);
    }

    @Override
    public List<Nota> buscarPorSerie(String serie) {
        if (serie == null) return List.of();
        return buscarTodos().stream().filter(n -> n.getSerie() != null && n.getSerie().equalsIgnoreCase(serie)).collect(Collectors.toList());
    }

    @Override
    public List<Nota> buscarPorVenda(String codigoVenda) {
        if (codigoVenda == null) return List.of();
        return buscarTodos().stream().filter(n -> n.getVenda() != null && n.getVenda().getCodigo() != null && n.getVenda().getCodigo().equalsIgnoreCase(codigoVenda)).collect(Collectors.toList());
    }

    @Override
    public List<Nota> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null) return List.of();
        return buscarTodos().stream().filter(n -> n.getDataEmissao() != null && (n.getDataEmissao().isEqual(inicio) || n.getDataEmissao().isAfter(inicio)) && (n.getDataEmissao().isEqual(fim) || n.getDataEmissao().isBefore(fim))).collect(Collectors.toList());
    }

    public void limparStore() {
        Map<String, Nota> store = SingletonMap.getInstance().getTypedStore(Nota.class);
        store.clear();
    }

    public int contar() {
        Map<String, Nota> store = SingletonMap.getInstance().getTypedStore(Nota.class);
        return store.size();
    }

    public Set<String> chaves() {
        Map<String, Nota> store = SingletonMap.getInstance().getTypedStore(Nota.class);
        return new LinkedHashSet<>(store.keySet());
    }

    public List<Nota> snapshot() {
        Map<String, Nota> store = SingletonMap.getInstance().getTypedStore(Nota.class);
        return new ArrayList<>(store.values());
    }
}
