package org.example.dao;

import org.example.domain.Contrato;

public class ContratoDao implements IContratoDao {

    @Override
    public void salvar(Contrato contrato) {
        throw new UnsupportedOperationException("Não funciona com o banco");
    }

    @Override
    public Contrato buscar(int numero) {
        throw new UnsupportedOperationException("Não funciona com o banco");
    }

    @Override
    public void atualizar(Contrato contrato) {
        throw new UnsupportedOperationException("Não funciona com o banco");
    }

    @Override
    public void excluir(int numero) {
        throw new UnsupportedOperationException("Não funciona com o banco");
    }
}
