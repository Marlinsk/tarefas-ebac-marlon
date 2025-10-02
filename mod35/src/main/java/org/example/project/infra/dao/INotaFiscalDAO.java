package org.example.project.infra.dao;

import org.example.project.common.generics.IGenericDAO;
import org.example.project.domain.entities.NotaFiscal;

import java.util.List;
import java.util.Optional;

public interface INotaFiscalDAO extends IGenericDAO<NotaFiscal, Integer> {
    Optional<NotaFiscal> findByChaveAcesso(String chaveAcesso);
    List<NotaFiscal> findByVenda(Integer vendaId);
}
