package org.example.project.test.service;

import org.example.project.domain.entities.Cliente;
import org.example.project.infra.dao.IClienteDAO;
import org.example.project.infra.services.impl.ClienteService;
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
public class ClienteServiceTest {

    @Mock private IClienteDAO dao;
    private ClienteService service;

    @Before
    public void setUp() {
        service = new ClienteService(dao);
    }

    @Test
    public void crud_basico() {
        Cliente c = new Cliente();
        when(dao.save(c)).thenReturn(c);
        when(dao.update(c)).thenReturn(c);
        when(dao.findById(1)).thenReturn(Optional.of(c));
        when(dao.findAll()).thenReturn(Arrays.asList(c));

        assertSame(c, service.criar(c));
        assertSame(c, service.atualizar(c));
        assertTrue(service.buscarPorId(1).isPresent());
        assertEquals(1, service.listar().size());

        service.remover(1);
        verify(dao).deleteById(1);
    }
}
