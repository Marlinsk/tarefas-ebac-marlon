package org.example.service;

import org.example.domain.Contrato;

public interface IContratoService {

    String salvar(Contrato contrato);

    Contrato buscar(int numero);

    String atualizar(Contrato contrato);

    String excluir(int numero);
}