package org.example.mocks.test.services;

import org.example.application.domain.Cliente;
import org.example.application.services.ClienteService;
import org.example.builder.ClienteBuilder;
import org.example.mocks.dao.MockClienteDAO;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClienteServiceMockTest {

    @Test
    public void deveCadastrarEBuscarClienteUsandoMock() {
        MockClienteDAO mockDAO = new MockClienteDAO();
        ClienteService service = new ClienteService(mockDAO);

        Cliente ana = ClienteBuilder.umCliente().comCpf(101L).comNome("Ana Paula").comEmail("ana@x.com").comTelefone("123").build();

        assertTrue(service.cadastrar(ana));
        assertEquals(1, mockDAO.size());

        assertNotNull(service.buscarPorCpf(101L));
        assertNotNull(service.buscarPorEmail("ANA@X.COM"));
        assertTrue(service.existePorTelefone("123"));
        assertEquals(1, service.buscarPorNome("paula").size());
    }

    @Test
    public void deveAlterarEExcluirUsandoMock() {
        MockClienteDAO mockDAO = new MockClienteDAO();
        ClienteService service = new ClienteService(mockDAO);

        Cliente c = ClienteBuilder.umCliente().comCpf(1L).comNome("Maria").build();
        service.cadastrar(c);
        c.setNome("Maria Clara");
        service.atualizar(c);

        assertEquals("Maria Clara", service.consultar(1L).getNome());
        service.excluir(1L);
        assertNull(service.consultar(1L));
    }
}
