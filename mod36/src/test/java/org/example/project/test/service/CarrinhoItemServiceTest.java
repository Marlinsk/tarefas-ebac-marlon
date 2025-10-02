package org.example.project.test.service;

import org.example.project.domain.entities.CarrinhoItem;
import org.example.project.infra.dao.ICarrinhoItemDAO;
import org.example.project.infra.services.impl.CarrinhoItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CarrinhoItemServiceTest {

    @Mock private ICarrinhoItemDAO dao;
    private CarrinhoItemService service;

    @Before
    public void setUp() {
        service = new CarrinhoItemService(dao);
    }

    @Test
    public void criarAtualizar_buscar_listar_remover() {
        CarrinhoItem item = new CarrinhoItem();
        when(dao.save(item)).thenReturn(item);
        when(dao.update(item)).thenReturn(item);
        when(dao.findById(1)).thenReturn(Optional.of(item));
        when(dao.findAll()).thenReturn(Arrays.asList(item));

        assertSame(item, service.criar(item));
        assertSame(item, service.atualizar(item));
        assertTrue(service.buscarPorId(1).isPresent());
        assertFalse(service.listar().isEmpty());

        service.remover(1);
        verify(dao).deleteById(1);
    }

    @Test
    public void listarEBuscarPorCarrinho_e_total() {
        CarrinhoItem i1 = new CarrinhoItem();
        List<CarrinhoItem> lista = Arrays.asList(i1);
        when(dao.findByCarrinho(10)).thenReturn(lista);
        when(dao.findByCarrinhoAndProduto(10, 5)).thenReturn(Optional.of(i1));
        when(dao.sumSubtotalByCarrinho(10)).thenReturn(new BigDecimal("123.45"));

        assertEquals(1, service.listarPorCarrinho(10).size());
        assertTrue(service.buscarPorCarrinhoEProduto(10, 5).isPresent());
        assertEquals(0, new BigDecimal("123.45").compareTo(service.totalDoCarrinho(10)));
    }

    @Test
    public void removerPorCarrinhoEProduto() {
        service.removerPorCarrinhoEProduto(10, 7);
        verify(dao).deleteByCarrinhoAndProduto(10, 7);
    }
}
