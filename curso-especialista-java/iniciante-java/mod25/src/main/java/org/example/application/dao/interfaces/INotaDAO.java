package org.example.application.dao.interfaces;

import org.example.application.dao.generic.IGenericDAO;
import org.example.application.domain.Nota;

import java.time.LocalDateTime;
import java.util.List;

public interface INotaDAO extends IGenericDAO<Nota, String> {
    Nota buscarPorChaveAcesso(String chaveAcesso);
    List<Nota> buscarPorSerie(String serie);
    List<Nota> buscarPorVenda(String codigoVenda);
    List<Nota> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
}
