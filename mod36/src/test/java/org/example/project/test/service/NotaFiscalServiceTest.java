package org.example.project.test.service;

import org.example.project.domain.entities.NotaFiscal;
import org.example.project.infra.dao.INotaFiscalDAO;
import org.example.project.infra.services.impl.NotaFiscalService;
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
public class NotaFiscalServiceTest {

    @Mock private INotaFiscalDAO dao;
    private NotaFiscalService service;

    @Before
    public void setUp() {
        service = new NotaFiscalService(dao);
    }

    @Test
    public void crud_e_buscas() {
        NotaFiscal nf = new NotaFiscal();
        when(dao.save(nf)).thenReturn(nf);
        when(dao.update(nf)).thenReturn(nf);
        when(dao.findById(1)).thenReturn(Optional.of(nf));
        when(dao.findByChaveAcesso("CH-1")).thenReturn(Optional.of(nf));
        when(dao.findByVenda(99)).thenReturn(Arrays.asList(nf));
        when(dao.findAll()).thenReturn(Arrays.asList(nf));

        assertSame(nf, service.criar(nf));
        assertSame(nf, service.atualizar(nf));

        assertTrue(service.buscarPorId(1).isPresent());
        assertTrue(service.buscarPorChaveAcesso("CH-1").isPresent());
        assertFalse(service.listarPorVenda(99).isEmpty());
        assertFalse(service.listar().isEmpty());

        service.remover(1);
        verify(dao).deleteById(1);
    }
}
