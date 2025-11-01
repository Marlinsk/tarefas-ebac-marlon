package org.example.mocks;

import org.example.dao.IContratoDao;
import org.example.domain.Contrato;

import java.util.HashMap;
import java.util.Map;

public class ContratoDaoMock implements IContratoDao {

    private final Map<Integer, Contrato> db = new HashMap<>();

    @Override
    public void salvar(Contrato contrato) {
        validarContrato(contrato);
        int numero = contrato.getNumero();
        if (db.containsKey(numero)) {
            throw new IllegalArgumentException("Já existe contrato com número " + numero);
        }
        db.put(numero, contrato);
    }

    @Override
    public Contrato buscar(int numero) {
        validarNumero(numero);
        return db.get(numero);
    }

    @Override
    public void atualizar(Contrato contrato) {
        validarContrato(contrato);
        int numero = contrato.getNumero();
        if (!db.containsKey(numero)) {
            throw new IllegalArgumentException("Não existe contrato com número " + numero + " para atualizar.");
        }
        db.put(numero, contrato);
    }

    @Override
    public void excluir(int numero) {
        validarNumero(numero);
        if (db.remove(numero) == null) {
            throw new IllegalArgumentException("Não existe contrato com número " + numero + " para excluir.");
        }
    }

    private void validarContrato(Contrato c) {
        if (c == null) throw new IllegalArgumentException("Contrato não pode ser nulo.");
        validarNumero(c.getNumero());
        if (c.getDataInicio() == null) {
            throw new IllegalArgumentException("Data de início é obrigatória.");
        }

        if (c.getValor() < 0) {
            throw new IllegalArgumentException("Valor do contrato não pode ser negativo.");
        }
    }

    private void validarNumero(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Número do contrato deve ser positivo.");
        }
    }
}
