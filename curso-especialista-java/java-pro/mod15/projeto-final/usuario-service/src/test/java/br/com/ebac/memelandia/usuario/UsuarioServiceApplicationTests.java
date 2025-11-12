package br.com.ebac.memelandia.usuario;

import br.com.ebac.memelandia.usuario.application.usecase.BuscarUsuarioPorEmailUseCaseTest;
import br.com.ebac.memelandia.usuario.application.usecase.BuscarUsuarioPorIdUseCaseTest;
import br.com.ebac.memelandia.usuario.application.usecase.CriarUsuarioUseCaseTest;
import br.com.ebac.memelandia.usuario.domain.entity.UsuarioTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Usuario Service Test Suite")
@SelectClasses({
    UsuarioTest.class,
    CriarUsuarioUseCaseTest.class,
    BuscarUsuarioPorIdUseCaseTest.class,
    BuscarUsuarioPorEmailUseCaseTest.class
})
class UsuarioServiceApplicationTests { }
