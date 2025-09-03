package org.example.service;

import org.example.application.dao.ClienteDAO;
import org.example.application.domain.Cliente;
import org.example.application.services.ClienteService;
import org.example.builder.ClienteBuilder;
import org.example.infra.BaseTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClienteServiceTest extends BaseTest {

    @Test
    public void deveUsarMetodosEspecificosDeCliente() {
        ClienteDAO dao = new ClienteDAO();
        ClienteService service = new ClienteService(dao);

        Cliente ana = ClienteBuilder.umCliente().comCpf(9L).comNome("Ana Paula").comEmail("ana@x.com").comTelefone("123").build();

        assertTrue(service.cadastrar(ana));
        assertNotNull(service.buscarPorCpf(9L));
        assertNotNull(service.buscarPorEmail("ANA@X.COM"));
        assertTrue(service.existePorTelefone("123"));
        assertEquals(1, service.buscarPorNome("Paula").size());
    }
}
