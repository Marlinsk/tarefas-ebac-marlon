package org.example.project.infra.services.impl;

import org.example.project.domain.entities.CarrinhoItem;
import org.example.project.infra.dao.ICarrinhoItemDAO;
import org.example.project.infra.dao.impl.CarrinhoItemDAO;
import org.example.project.infra.services.ICarrinhoItemService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CarrinhoItemService implements ICarrinhoItemService {

    private final ICarrinhoItemDAO dao;

    public CarrinhoItemService(ICarrinhoItemDAO dao) {
        this.dao = dao;
    }

    public CarrinhoItemService(String url, String user, String pass) {
        this.dao = new CarrinhoItemDAO(url, user, pass);
    }

    @Override
    public CarrinhoItem criar(CarrinhoItem item) {
        return dao.save(item);
    }

    @Override
    public CarrinhoItem atualizar(CarrinhoItem item) {
        return dao.update(item);
    }

    @Override
    public Optional<CarrinhoItem> buscarPorId(Integer id) {
        return dao.findById(id);
    }

    @Override
    public List<CarrinhoItem> listar() {
        return dao.findAll();
    }

    @Override
    public List<CarrinhoItem> listarPorCarrinho(Integer carrinhoId) {
        return dao.findByCarrinho(carrinhoId);
    }

    @Override
    public Optional<CarrinhoItem> buscarPorCarrinhoEProduto(Integer carrinhoId, Integer produtoId) {
        return dao.findByCarrinhoAndProduto(carrinhoId, produtoId);
    }

    @Override
    public void remover(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public void removerPorCarrinhoEProduto(Integer carrinhoId, Integer produtoId) {
        dao.deleteByCarrinhoAndProduto(carrinhoId, produtoId);
    }

    @Override
    public BigDecimal totalDoCarrinho(Integer carrinhoId) {
        return dao.sumSubtotalByCarrinho(carrinhoId);
    }
}
