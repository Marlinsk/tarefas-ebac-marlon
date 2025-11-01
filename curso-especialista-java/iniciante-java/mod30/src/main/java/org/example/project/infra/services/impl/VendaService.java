package org.example.project.infra.services.impl;

import org.example.project.infra.dao.IVendaDAO;
import org.example.project.infra.dao.impl.VendaDAO;
import org.example.project.domain.entities.Venda;
import org.example.project.infra.services.IVendaService;

import java.util.List;
import java.util.Optional;

public class VendaService implements IVendaService {

    private final IVendaDAO dao;

    public VendaService(IVendaDAO dao) {
        this.dao = dao;
    }

    public VendaService(String url, String user, String pass) {
        this.dao = new VendaDAO(url, user, pass);
    }

    @Override
    public Venda criar(Venda v) {
        return dao.save(v);
    }

    @Override
    public Venda atualizar(Venda v) {
        return dao.update(v);
    }

    @Override
    public Optional<Venda> buscarPorId(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Optional<Venda> buscarPorNumero(String numero) {
        return dao.findByNumero(numero);
    }

    @Override
    public List<Venda> listar() {
        return dao.findAll();
    }

    @Override
    public List<Venda> listarPorCliente(Integer clienteId) {
        return dao.findByCliente(clienteId);
    }

    @Override
    public void remover(Integer id) {
        dao.deleteById(id);
    }
}
