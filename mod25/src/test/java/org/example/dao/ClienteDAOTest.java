package org.example.dao;

import org.example.application.dao.ClienteDAO;
import org.example.application.domain.Cliente;
import org.example.builder.ClienteBuilder;
import org.example.infra.BaseTest;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ClienteDAOTest extends BaseTest {

    @Test
    public void deveCadastrarConsultarEAlterarCliente() {
        ClienteDAO dao = new ClienteDAO();
        Cliente c = ClienteBuilder.umCliente().comCpf(123L).comNome("Maria").build();

        assertTrue(dao.cadastrar(c));
        Cliente salvo = dao.consultar(123L);
        assertNotNull(salvo);
        assertEquals("Maria", salvo.getNome());

        salvo.setNome("Maria Clara");
        dao.atualizar(salvo);

        Cliente atualizado = dao.consultar(123L);
        assertEquals("Maria Clara", atualizado.getNome());
    }

    @Test
    public void deveBuscarPorEmailENomeETelefone() {
        ClienteDAO dao = new ClienteDAO();
        dao.cadastrar(ClienteBuilder.umCliente().comCpf(1L).comNome("Ana Paula").comEmail("ana@x.com").comTelefone("1111").build());
        dao.cadastrar(ClienteBuilder.umCliente().comCpf(2L).comNome("Paulo").comEmail("paulo@x.com").comTelefone("2222").build());

        assertNotNull(dao.buscarPorEmail("ANA@x.com"));
        List<Cliente> porNome = dao.buscarPorNome("Pa");
        assertEquals(2, porNome.size());
        assertTrue(dao.existePorTelefone("1111"));
    }
}
