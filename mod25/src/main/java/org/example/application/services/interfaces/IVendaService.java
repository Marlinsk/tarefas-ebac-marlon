package org.example.application.services.interfaces;

import org.example.application.domain.Venda;
import org.example.application.services.generic.IGenericService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IVendaService extends IGenericService<Venda, String> {
    List<Venda> buscarPorCliente(Long cpfCliente);
    List<Venda> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
    List<Venda> buscarPorFaixaDeValor(BigDecimal valorMin, BigDecimal valorMax);
    List<Venda> buscarPorProduto(String codigoProduto);
}
