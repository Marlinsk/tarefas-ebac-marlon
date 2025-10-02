package org.example.project.test.service;

import org.example.project.domain.entities.Produto;
import org.example.project.infra.dao.IProdutoDAO;
import org.example.project.infra.services.impl.ProdutoService;
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
public class ProdutoServiceTest {

    @Mock private IProdutoDAO dao;
    private ProdutoService service;

    @Before
    public void setUp() {
        service = new ProdutoService(dao);
    }

    @Test
    public void crud_e_buscaPorCodigo() {
        Produto p = new Produto();
        when(dao.save(p)).thenReturn(p);
        when(dao.update(p)).thenReturn(p);
        when(dao.findById(1)).thenReturn(Optional.of(p));
        when(dao.findByCodigo("ABC")).thenReturn(Optional.of(p));
        when(dao.findAll()).thenReturn(Arrays.asList(p));

        assertSame(p, service.criar(p));
        assertSame(p, service.atualizar(p));
        assertTrue(service.buscarPorId(1).isPresent());
        assertTrue(service.buscarPorCodigo("ABC").isPresent());
        assertFalse(service.listar().isEmpty());

        service.remover(1);
        verify(dao).deleteById(1);
    }
}
