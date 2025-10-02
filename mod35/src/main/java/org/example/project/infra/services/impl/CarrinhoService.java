package org.example.project.infra.services.impl;

import jakarta.persistence.EntityManager;
import org.example.project.domain.entities.Carrinho;
import org.example.project.domain.entities.CarrinhoItem;
import org.example.project.infra.dao.ICarrinhoDAO;
import org.example.project.infra.dao.ICarrinhoItemDAO;
import org.example.project.infra.dao.impl.CarrinhoDAO;
import org.example.project.infra.dao.impl.CarrinhoItemDAO;
import org.example.project.infra.services.ICarrinhoService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CarrinhoService implements ICarrinhoService {

    private final ICarrinhoDAO carrinhoDAO;
    private final ICarrinhoItemDAO itemDAO;

    public CarrinhoService(ICarrinhoDAO carrinhoDAO, ICarrinhoItemDAO itemDAO) {
        this.carrinhoDAO = carrinhoDAO;
        this.itemDAO = itemDAO;
    }

    public CarrinhoService(EntityManager entityManager) {
        this.carrinhoDAO = new CarrinhoDAO(entityManager);
        this.itemDAO = new CarrinhoItemDAO(entityManager);
    }

    @Override
    public Carrinho criar(Carrinho c) {
        return carrinhoDAO.save(c);
    }

    @Override
    public Carrinho atualizar(Carrinho c) {
        return carrinhoDAO.update(c);
    }

    @Override
    public Optional<Carrinho> buscarPorId(Integer id) {
        return carrinhoDAO.findById(id);
    }

    @Override
    public Optional<Carrinho> buscarPorCodigo(String codigo) {
        return carrinhoDAO.findByCodigo(codigo);
    }

    @Override
    public List<Carrinho> listar() {
        return carrinhoDAO.findAll();
    }

    @Override
    public List<Carrinho> listarPorCliente(Integer clienteId) {
        return carrinhoDAO.findByCliente(clienteId);
    }

    @Override
    public void remover(Integer id) {
        carrinhoDAO.deleteById(id);
    }

    @Override
    public Carrinho adicionarItem(Integer carrinhoId, Integer produtoId, int quantidade, BigDecimal precoUnitario) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser > 0");

        BigDecimal subtotal = precoUnitario.multiply(BigDecimal.valueOf(quantidade));

        Optional<CarrinhoItem> existente = itemDAO.findByCarrinhoAndProduto(carrinhoId, produtoId);
        if (existente.isPresent()) {
            CarrinhoItem it = existente.get();
            it.setQuantidade(it.getQuantidade() + quantidade);
            it.setPrecoUnitario(precoUnitario);
            it.setSubtotal(precoUnitario.multiply(BigDecimal.valueOf(it.getQuantidade())));
            itemDAO.update(it);
        } else {
            CarrinhoItem novo = new CarrinhoItem(carrinhoId, produtoId, quantidade, precoUnitario, subtotal);
            itemDAO.save(novo);
        }

        BigDecimal total = itemDAO.sumSubtotalByCarrinho(carrinhoId);
        carrinhoDAO.atualizarTotal(carrinhoId, total);

        Carrinho carrinho = carrinhoDAO.findById(carrinhoId).orElseThrow(() -> new IllegalStateException("Carrinho não encontrado após atualizar total"));
        carrinho.setTotal(total);
        return carrinho;
    }

    @Override
    public Carrinho atualizarQuantidade(Integer carrinhoId, Integer produtoId, int novaQuantidade, BigDecimal precoUnitario) {
        if (novaQuantidade < 0) throw new IllegalArgumentException("Quantidade não pode ser negativa");

        Optional<CarrinhoItem> existente = itemDAO.findByCarrinhoAndProduto(carrinhoId, produtoId);
        if (!existente.isPresent()) throw new IllegalStateException("Item não existe no carrinho");

        if (novaQuantidade == 0) {
            itemDAO.deleteByCarrinhoAndProduto(carrinhoId, produtoId);
        } else {
            CarrinhoItem it = existente.get();
            it.setQuantidade(novaQuantidade);
            it.setPrecoUnitario(precoUnitario);
            it.setSubtotal(precoUnitario.multiply(BigDecimal.valueOf(novaQuantidade)));
            itemDAO.update(it);
        }

        BigDecimal total = itemDAO.sumSubtotalByCarrinho(carrinhoId);
        carrinhoDAO.atualizarTotal(carrinhoId, total);

        Carrinho carrinho = carrinhoDAO.findById(carrinhoId).orElseThrow(() -> new IllegalStateException("Carrinho não encontrado após atualizar total"));
        carrinho.setTotal(total);
        return carrinho;
    }

    @Override
    public Carrinho removerItem(Integer carrinhoId, Integer produtoId) {
        itemDAO.deleteByCarrinhoAndProduto(carrinhoId, produtoId);

        BigDecimal total = itemDAO.sumSubtotalByCarrinho(carrinhoId);
        carrinhoDAO.atualizarTotal(carrinhoId, total);

        Carrinho carrinho = carrinhoDAO.findById(carrinhoId).orElseThrow(() -> new IllegalStateException("Carrinho não encontrado após atualizar total"));
        carrinho.setTotal(total);
        return carrinho;
    }
}
