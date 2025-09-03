package org.example.application.services.interfaces;

import org.example.application.domain.Nota;
import org.example.application.services.generic.IGenericService;

import java.time.LocalDateTime;
import java.util.List;

public interface INotaService extends IGenericService<Nota, String> {
    Nota buscarPorChaveAcesso(String chaveAcesso);
    List<Nota> buscarPorSerie(String serie);
    List<Nota> buscarPorVenda(String codigoVenda);
    List<Nota> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
}
