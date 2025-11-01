package org.example.mocks.dao;

import org.example.application.dao.interfaces.INotaDAO;
import org.example.application.domain.Nota;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MockNotaDAO extends BaseMockDAO<Nota, String> implements INotaDAO {

    @Override
    protected String getKey(Nota n) {
        return n.getCodigo();
    }

    @Override
    public Nota buscarPorChaveAcesso(String chaveAcesso) {
        if (chaveAcesso == null) return null;
        return store.values().stream().filter(n -> n.getChaveAcesso() != null && n.getChaveAcesso().equalsIgnoreCase(chaveAcesso)).findFirst().orElse(null);
    }

    @Override
    public List<Nota> buscarPorSerie(String serie) {
        if (serie == null) return List.of();
        String s = serie.toLowerCase();
        return store.values().stream().filter(n -> n.getSerie() != null && n.getSerie().toLowerCase().equals(s)).collect(Collectors.toList());
    }

    @Override
    public List<Nota> buscarPorVenda(String codigoVenda) {
        if (codigoVenda == null) return List.of();
        String cod = codigoVenda.toLowerCase();
        return store.values().stream().filter(n -> n.getVenda() != null && n.getVenda().getCodigo() != null && n.getVenda().getCodigo().toLowerCase().equals(cod)).collect(Collectors.toList());
    }

    @Override
    public List<Nota> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null) return List.of();
        return store.values().stream().filter(n -> n.getDataEmissao() != null && (n.getDataEmissao().isEqual(inicio) || n.getDataEmissao().isAfter(inicio)) && (n.getDataEmissao().isEqual(fim) || n.getDataEmissao().isBefore(fim))).collect(Collectors.toList());
    }
}
