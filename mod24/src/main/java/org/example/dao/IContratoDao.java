package org.example.dao;

import org.example.domain.Contrato;

public interface IContratoDao {

    void salvar(Contrato contrato);

    Contrato buscar(int numero);

    void atualizar(Contrato contrato);

    void excluir(int numero);

}
