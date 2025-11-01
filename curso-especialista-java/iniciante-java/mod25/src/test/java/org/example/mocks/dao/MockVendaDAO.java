package org.example.mocks.dao;

import org.example.application.dao.interfaces.IVendaDAO;
import org.example.application.domain.Venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MockVendaDAO extends BaseMockDAO<Venda, String> implements IVendaDAO {

    @Override
    protected String getKey(Venda v) {
        return v.getCodigo();
    }

    @Override
    public List<Venda> buscarPorCliente(Long cpfCliente) {
        if (cpfCliente == null) return List.of();
        return store.values().stream().filter(v -> v.getCliente() != null && v.getCliente().getCpf() != null && v.getCliente().getCpf().equals(cpfCliente)).collect(Collectors.toList());
    }

    @Override
    public List<Venda> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null) return List.of();
        return store.values().stream().filter(v -> v.getDataCriacao() != null && (v.getDataCriacao().isEqual(inicio) || v.getDataCriacao().isAfter(inicio)) && (v.getDataCriacao().isEqual(fim) || v.getDataCriacao().isBefore(fim))).collect(Collectors.toList());
    }

    @Override
    public List<Venda> buscarPorFaixaDeValor(BigDecimal valorMin, BigDecimal valorMax) {
        if (valorMin == null || valorMax == null) return List.of();
        return store.values().stream().filter(v -> v.getValorTotal() != null && v.getValorTotal().compareTo(valorMin) >= 0 && v.getValorTotal().compareTo(valorMax) <= 0).collect(Collectors.toList());
    }

    @Override
    public List<Venda> buscarPorProduto(String codigoProduto) {
        if (codigoProduto == null) return List.of();
        String cod = codigoProduto.toLowerCase();
        return store.values().stream().filter(v -> v.getItens().stream().anyMatch(pq -> pq.getProduto() != null && pq.getProduto().getCodigo() != null && pq.getProduto().getCodigo().toLowerCase().equals(cod))).collect(Collectors.toList());
    }
}
