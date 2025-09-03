package org.example.application.services;

import org.example.application.dao.interfaces.INotaDAO;
import org.example.application.domain.Nota;
import org.example.application.services.generic.GenericService;
import org.example.application.services.interfaces.INotaService;

import java.time.LocalDateTime;
import java.util.List;

public class NotaService extends GenericService<Nota, String> implements INotaService {

    private final INotaDAO notaDAO;

    public NotaService(INotaDAO notaDAO) {
        super(notaDAO);
        this.notaDAO = notaDAO;
    }

    @Override
    public Nota buscarPorChaveAcesso(String chaveAcesso) {
        return notaDAO.buscarPorChaveAcesso(chaveAcesso);
    }

    @Override
    public List<Nota> buscarPorSerie(String serie) {
        return notaDAO.buscarPorSerie(serie);
    }

    @Override
    public List<Nota> buscarPorVenda(String codigoVenda) {
        return notaDAO.buscarPorVenda(codigoVenda);
    }

    @Override
    public List<Nota> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return notaDAO.buscarPorPeriodo(inicio, fim);
    }
}
