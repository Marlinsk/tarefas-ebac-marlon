package org.example.project.test.service;

import org.example.project.domain.entities.Venda;
import org.example.project.infra.dao.IVendaDAO;
import org.example.project.infra.services.impl.VendaService;
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
public class VendaServiceTest {

    @Mock private IVendaDAO dao;
    private VendaService service;

    @Before
    public void setUp() {
        service = new VendaService(dao);
    }

    @Test
    public void crud_e_buscas() {
        Venda v = new Venda();
        when(dao.save(v)).thenReturn(v);
        when(dao.update(v)).thenReturn(v);
        when(dao.findById(1)).thenReturn(Optional.of(v));
        when(dao.findByNumero("V-1")).thenReturn(Optional.of(v));
        when(dao.findByCliente(99)).thenReturn(Arrays.asList(v));
        when(dao.findAll()).thenReturn(Arrays.asList(v));

        assertSame(v, service.criar(v));
        assertSame(v, service.atualizar(v));
        assertTrue(service.buscarPorId(1).isPresent());
        assertTrue(service.buscarPorNumero("V-1").isPresent());
        assertFalse(service.listarPorCliente(99).isEmpty());
        assertFalse(service.listar().isEmpty());

        service.remover(1);
        verify(dao).deleteById(1);
    }
}
