package org.example.application.services;

import org.example.application.dao.interfaces.IVendaDAO;
import org.example.application.domain.Venda;
import org.example.application.services.generic.GenericService;
import org.example.application.services.interfaces.IVendaService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class VendaService extends GenericService<Venda, String> implements IVendaService {

    private final IVendaDAO vendaDAO;

    public VendaService(IVendaDAO vendaDAO) {
        super(vendaDAO);
        this.vendaDAO = vendaDAO;
    }

    @Override
    public List<Venda> buscarPorCliente(Long cpfCliente) {
        return vendaDAO.buscarPorCliente(cpfCliente);
    }

    @Override
    public List<Venda> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return vendaDAO.buscarPorPeriodo(inicio, fim);
    }

    @Override
    public List<Venda> buscarPorFaixaDeValor(BigDecimal valorMin, BigDecimal valorMax) {
        return vendaDAO.buscarPorFaixaDeValor(valorMin, valorMax);
    }

    @Override
    public List<Venda> buscarPorProduto(String codigoProduto) {
        return vendaDAO.buscarPorProduto(codigoProduto);
    }
}
