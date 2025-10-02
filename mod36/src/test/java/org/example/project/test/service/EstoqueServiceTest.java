package org.example.project.test.service;

import org.example.project.domain.entities.Estoque;
import org.example.project.infra.dao.IEstoqueDAO;
import org.example.project.infra.services.impl.EstoqueService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EstoqueServiceTest {

    @Mock private IEstoqueDAO dao;
    private EstoqueService service;

    @Before
    public void setUp() {
        service = new EstoqueService(dao);
    }

    @Test
    public void crud_basico_e_buscas() {
        Estoque e = new Estoque();
        when(dao.save(e)).thenReturn(e);
        when(dao.update(e)).thenReturn(e);
        when(dao.findById(1)).thenReturn(Optional.of(e));
        when(dao.findByProdutoId(5)).thenReturn(Optional.of(e));
        when(dao.findAll()).thenReturn(Arrays.asList(e));

        assertSame(e, service.criar(e));
        assertSame(e, service.atualizar(e));
        assertTrue(service.buscarPorId(1).isPresent());
        assertTrue(service.buscarPorProduto(5).isPresent());
        assertFalse(service.listar().isEmpty());

        service.remover(1);
        verify(dao).deleteById(1);
    }

    @Test
    public void entrada_saida() {
        service.entrada(7, 3);
        service.saida(7, 2);
        verify(dao).incrementar(7, 3);
        verify(dao).decrementar(7, 2);
    }
}
