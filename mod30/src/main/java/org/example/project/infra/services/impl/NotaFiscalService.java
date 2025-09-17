package org.example.project.infra.services.impl;

import org.example.project.domain.entities.NotaFiscal;
import org.example.project.infra.dao.INotaFiscalDAO;
import org.example.project.infra.dao.impl.NotaFiscalDAO;
import org.example.project.infra.services.INotaFiscalService;

import java.util.List;
import java.util.Optional;

public class NotaFiscalService implements INotaFiscalService {

    private final INotaFiscalDAO dao;

    public NotaFiscalService(INotaFiscalDAO dao) {
        this.dao = dao;
    }

    public NotaFiscalService(String url, String user, String pass) {
        this.dao = new NotaFiscalDAO(url, user, pass);
    }

    @Override
    public NotaFiscal criar(NotaFiscal n) {
        return dao.save(n);
    }

    @Override
    public NotaFiscal atualizar(NotaFiscal n) {
        return dao.update(n);
    }

    @Override
    public Optional<NotaFiscal> buscarPorId(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Optional<NotaFiscal> buscarPorChaveAcesso(String chaveAcesso) {
        return dao.findByChaveAcesso(chaveAcesso);
    }

    @Override
    public List<NotaFiscal> listar() {
        return dao.findAll();
    }

    @Override
    public List<NotaFiscal> listarPorVenda(Integer vendaId) {
        return dao.findByVenda(vendaId);
    }

    @Override
    public void remover(Integer id) {
        dao.deleteById(id);
    }
}
