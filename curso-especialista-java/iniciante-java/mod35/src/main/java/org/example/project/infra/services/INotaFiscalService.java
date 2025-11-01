package org.example.project.infra.services;

import org.example.project.domain.entities.NotaFiscal;

import java.util.List;
import java.util.Optional;

public interface INotaFiscalService {
    NotaFiscal criar(NotaFiscal n);
    NotaFiscal atualizar(NotaFiscal n);
    Optional<NotaFiscal> buscarPorId(Integer id);
    Optional<NotaFiscal> buscarPorChaveAcesso(String chaveAcesso);
    List<NotaFiscal> listar();
    List<NotaFiscal> listarPorVenda(Integer vendaId);
    void remover(Integer id);
}
