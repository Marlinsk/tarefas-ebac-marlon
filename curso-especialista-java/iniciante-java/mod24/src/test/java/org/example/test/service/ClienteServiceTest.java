package org.example.test.service;

import org.example.dao.IClienteDao;
import org.example.domain.Cliente;
import org.example.mocks.ClienteDaoMock;
import org.example.service.ClienteService;
import org.junit.Assert;
import org.junit.Test;

public class ClienteServiceTest {

    @Test
    public void salvarComSucesso() {
        IClienteDao dao = new ClienteDaoMock();
        ClienteService service = new ClienteService(dao);

        Cliente c = new Cliente("Ana", "111", "ana@email.com");
        String msg = service.salvar(c);

        Assert.assertNotNull("Mensagem não deve ser nula", msg);
        Assert.assertTrue("Mensagem deveria indicar sucesso", msg.toLowerCase().contains("sucesso"));
        Assert.assertNotNull("Cliente precisa existir após salvar", service.buscar("111"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void salvarDuplicadoDeveLancarExcecao() {
        IClienteDao dao = new ClienteDaoMock();
        ClienteService service = new ClienteService(dao);

        Cliente c1 = new Cliente("Ana", "222", "a@a.com");
        service.salvar(c1);

        Cliente c2 = new Cliente("Outra Ana", "222", "b@b.com");
        service.salvar(c2);
    }

    @Test
    public void buscarRetornaNullQuandoNaoExiste() {
        IClienteDao dao = new ClienteDaoMock();
        ClienteService service = new ClienteService(dao);

        Assert.assertNull(service.buscar("nao-existe"));
    }

    @Test
    public void atualizarComSucesso() {
        IClienteDao dao = new ClienteDaoMock();
        ClienteService service = new ClienteService(dao);

        Cliente original = new Cliente("Bob", "333", "old@ex.com");
        service.salvar(original);

        Cliente atualizado = new Cliente("Bob Jr", "333", "new@ex.com");
        String msg = service.atualizar(atualizado);

        Assert.assertNotNull(msg);
        Assert.assertTrue(msg.toLowerCase().contains("sucesso"));

        Cliente lido = service.buscar("333");
        Assert.assertEquals("Bob Jr", lido.getNome());
        Assert.assertEquals("new@ex.com", lido.getEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void atualizarInexistenteDeveLancarExcecao() {
        IClienteDao dao = new ClienteDaoMock();
        ClienteService service = new ClienteService(dao);

        Cliente naoExiste = new Cliente("Zoe", "999", "z@z.com");
        service.atualizar(naoExiste);
    }

    @Test
    public void excluirComSucesso() {
        IClienteDao dao = new ClienteDaoMock();
        ClienteService service = new ClienteService(dao);

        Cliente c = new Cliente("Mia", "444", "m@m.com");
        service.salvar(c);

        String msg = service.excluir("444");
        Assert.assertNotNull(msg);
        Assert.assertTrue(msg.toLowerCase().contains("sucesso"));
        Assert.assertNull(service.buscar("444"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void excluirInexistenteDeveLancarExcecao() {
        IClienteDao dao = new ClienteDaoMock();
        ClienteService service = new ClienteService(dao);
        service.excluir("000");
    }
}
