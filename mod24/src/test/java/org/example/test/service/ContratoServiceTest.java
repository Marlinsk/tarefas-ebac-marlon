package org.example.test.service;

import org.example.dao.IContratoDao;
import org.example.domain.Contrato;
import org.example.mocks.ContratoDaoMock;
import org.example.service.ContratoService;
import org.example.service.IContratoService;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class ContratoServiceTest {

    @Test
    public void salvarComSucesso() {
        IContratoDao dao = new ContratoDaoMock();
        IContratoService service = new ContratoService(dao);

        Contrato c = new Contrato(1, LocalDate.now(), 1000.0);
        String msg = service.salvar(c);

        Assert.assertNotNull(msg);
        Assert.assertTrue(msg.toLowerCase().contains("sucesso"));
        Assert.assertNotNull(service.buscar(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void salvarDuplicadoDeveLancarExcecao() {
        IContratoDao dao = new ContratoDaoMock();
        IContratoService service = new ContratoService(dao);
        service.salvar(new Contrato(10, LocalDate.now(), 200.0));
        service.salvar(new Contrato(10, LocalDate.now(), 300.0));
    }

    @Test
    public void buscarRetornaNullQuandoNaoExiste() {
        IContratoDao dao = new ContratoDaoMock();
        IContratoService service = new ContratoService(dao);
        Assert.assertNull(service.buscar(9999));
    }

    @Test
    public void atualizarComSucesso() {
        IContratoDao dao = new ContratoDaoMock();
        IContratoService service = new ContratoService(dao);

        service.salvar(new Contrato(2, LocalDate.of(2025, 1, 1), 500.0));
        String msg = service.atualizar(new Contrato(2, LocalDate.of(2025, 2, 1), 750.0));

        Assert.assertNotNull(msg);
        Assert.assertTrue(msg.toLowerCase().contains("sucesso"));

        Contrato lido = service.buscar(2);
        Assert.assertEquals(LocalDate.of(2025, 2, 1), lido.getDataInicio());
        Assert.assertEquals(750.0, lido.getValor(), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void atualizarInexistenteDeveLancarExcecao() {
        IContratoDao dao = new ContratoDaoMock();
        IContratoService service = new ContratoService(dao);
        service.atualizar(new Contrato(404, LocalDate.now(), 1.0));
    }

    @Test
    public void excluirComSucesso() {
        IContratoDao dao = new ContratoDaoMock();
        IContratoService service = new ContratoService(dao);

        service.salvar(new Contrato(3, LocalDate.now(), 100.0));
        String msg = service.excluir(3);

        Assert.assertNotNull(msg);
        Assert.assertTrue(msg.toLowerCase().contains("sucesso"));
        Assert.assertNull(service.buscar(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void excluirInexistenteDeveLancarExcecao() {
        IContratoDao dao = new ContratoDaoMock();
        IContratoService service = new ContratoService(dao);
        service.excluir(0);
    }
}
