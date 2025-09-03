package org.example.application.dao.interfaces;

import org.example.application.dao.generic.IGenericDAO;
import org.example.application.domain.Venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IVendaDAO extends IGenericDAO<Venda, String> {
    List<Venda> buscarPorCliente(Long cpfCliente);
    List<Venda> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
    List<Venda> buscarPorFaixaDeValor(BigDecimal valorMin, BigDecimal valorMax);
    List<Venda> buscarPorProduto(String codigoProduto);
}
