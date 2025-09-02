package org.example.service;

import org.example.dao.IContratoDao;
import org.example.domain.Contrato;

public class ContratoService implements IContratoService {

    private final IContratoDao contratoDao;

    public ContratoService(IContratoDao dao) {
        this.contratoDao = dao;
    }

    @Override
    public String salvar(Contrato contrato) {
        contratoDao.salvar(contrato);
        return "Contrato salvo com sucesso!";
    }

    @Override
    public Contrato buscar(int numero) {
        return contratoDao.buscar(numero);
    }

    @Override
    public String atualizar(Contrato contrato) {
        contratoDao.atualizar(contrato);
        return "Contrato atualizado com sucesso!";
    }

    @Override
    public String excluir(int numero) {
        contratoDao.excluir(numero);
        return "Contrato excluído com sucesso!";
    }
}
