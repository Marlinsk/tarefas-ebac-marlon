package org.example.application.services;

import org.example.application.dao.interfaces.ICarrinhoDAO;
import org.example.application.domain.Carrinho;
import org.example.application.domain.Cliente;
import org.example.application.domain.Produto;
import org.example.application.domain.ProdutoQuantidade;
import org.example.application.domain.Venda;
import org.example.application.services.generic.GenericService;
import org.example.application.services.interfaces.ICarrinhoService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarrinhoService extends GenericService<Carrinho, String> implements ICarrinhoService {

    private final ICarrinhoDAO carrinhoDAO;

    public CarrinhoService(ICarrinhoDAO carrinhoDAO) {
        super(carrinhoDAO);
        this.carrinhoDAO = carrinhoDAO;
    }

    @Override
    public Carrinho criarCarrinho(Cliente cliente) {
        Objects.requireNonNull(cliente, "Cliente não pode ser nulo");
        Carrinho c = new Carrinho();
        c.setCliente(cliente);
        dao.cadastrar(c);
        return c;
    }

    @Override
    public void adicionarProduto(String codigoCarrinho, Produto produto, int quantidade) {
        carrinhoDAO.adicionarProduto(codigoCarrinho, produto, quantidade);
    }

    @Override
    public void removerProduto(String codigoCarrinho, Produto produto, int quantidade) {
        carrinhoDAO.removerProduto(codigoCarrinho, produto, quantidade);
    }

    @Override
    public BigDecimal calcularTotal(String codigoCarrinho) {
        return carrinhoDAO.calcularTotal(codigoCarrinho);
    }

    @Override
    public Venda finalizarVenda(String codigoCarrinho, String codigoVenda) {
        return carrinhoDAO.finalizarVenda(codigoCarrinho, codigoVenda);
    }

    @Override
    public void limparCarrinho(String codigoCarrinho) {
        Carrinho c = exigirCarrinho(codigoCarrinho);
        List<ProdutoQuantidade> itensSnapshot = new ArrayList<>(c.getItens());
        for (ProdutoQuantidade pq : itensSnapshot) {
            c.removerProduto(pq.getProduto(), pq.getQuantidade());
        }
        dao.atualizar(c);
    }

    @Override
    public void definirCliente(String codigoCarrinho, Cliente cliente) {
        Objects.requireNonNull(cliente, "Cliente não pode ser nulo");
        Carrinho c = exigirCarrinho(codigoCarrinho);
        c.setCliente(cliente);
        dao.atualizar(c);
    }

    @Override
    public boolean contemProduto(String codigoCarrinho, Produto produto) {
        Objects.requireNonNull(produto, "Produto não pode ser nulo");
        Carrinho c = exigirCarrinho(codigoCarrinho);
        String cod = produto.getCodigo();
        return c.getItens().stream().anyMatch(pq -> pq.getProduto() != null && Objects.equals(cod, pq.getProduto().getCodigo()));
    }

    @Override
    public int quantidadeTotalItens(String codigoCarrinho) {
        Carrinho c = exigirCarrinho(codigoCarrinho);
        return c.getItens().stream().mapToInt(ProdutoQuantidade::getQuantidade).sum();
    }

    @Override
    public List<?> listarItens(String codigoCarrinho) {
        Carrinho c = exigirCarrinho(codigoCarrinho);
        return new ArrayList<>(c.getItens());
    }

    private Carrinho exigirCarrinho(String codigoCarrinho) {
        Objects.requireNonNull(codigoCarrinho, "Código do carrinho não pode ser nulo");
        Carrinho c = dao.consultar(codigoCarrinho);
        if (c == null) {
            throw new IllegalStateException("Carrinho com código [" + codigoCarrinho + "] não encontrado");
        }
        return c;
    }
}
