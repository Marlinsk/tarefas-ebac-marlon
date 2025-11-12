package br.com.ebac.memelandia.categoria.application.usecase;

import br.com.ebac.memelandia.categoria.domain.entities.CategoriaMeme;
import br.com.ebac.memelandia.categoria.domain.exception.UsuarioInvalidoException;
import br.com.ebac.memelandia.categoria.domain.repository.CategoriaMemeRepository;
import br.com.ebac.memelandia.categoria.infrastructure.config.UsuarioClient;
import br.com.ebac.memelandia.categoria.infrastructure.config.UsuarioResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do CriarCategoriaMemeUseCase")
class CriarCategoriaMemeUseCaseTest {

    @Mock
    private CategoriaMemeRepository categoriaMemeRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private CriarCategoriaMemeUseCase criarCategoriaMemeUseCase;

    private String nome;
    private String descricao;
    private Long usuarioId;

    @BeforeEach
    void setUp() {
        nome = "Categoria Teste";
        descricao = "Descrição da categoria teste";
        usuarioId = 1L;
    }

    @Test
    @DisplayName("Deve criar categoria com sucesso quando usuário é válido")
    void deveCriarCategoriaComSucesso() {
        CategoriaMeme categoriaEsperada = CategoriaMeme.criar(nome, descricao, usuarioId);
        categoriaEsperada.setId(1L);

        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());

        when(categoriaMemeRepository.salvar(any(CategoriaMeme.class))).thenReturn(categoriaEsperada);
        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);

        CategoriaMeme resultado = criarCategoriaMemeUseCase.executar(nome, descricao, usuarioId);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(nome, resultado.getNome());
        assertEquals(descricao, resultado.getDescricao());
        assertEquals(usuarioId, resultado.getUsuarioId());
        assertNotNull(resultado.getDataCadastro());

        verify(usuarioClient, times(1)).buscarUsuarioPorId(usuarioId);
        verify(categoriaMemeRepository, times(1)).salvar(any(CategoriaMeme.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não existe")
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenThrow(new RuntimeException("Usuário não encontrado"));

        assertThrows(UsuarioInvalidoException.class, () -> criarCategoriaMemeUseCase.executar(nome, descricao, usuarioId));

        verify(usuarioClient, times(1)).buscarUsuarioPorId(usuarioId);
        verify(categoriaMemeRepository, never()).salvar(any(CategoriaMeme.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome é inválido - menos de 3 caracteres")
    void deveLancarExcecaoQuandoNomeInvalidoCurto() {
        String nomeInvalido = "ab";
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());
        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);

        assertThrows(IllegalArgumentException.class, () -> criarCategoriaMemeUseCase.executar(nomeInvalido, descricao, usuarioId));

        verify(usuarioClient, times(1)).buscarUsuarioPorId(usuarioId);
        verify(categoriaMemeRepository, never()).salvar(any(CategoriaMeme.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome é nulo")
    void deveLancarExcecaoQuandoNomeNulo() {
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());
        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);

        assertThrows(IllegalArgumentException.class, () -> criarCategoriaMemeUseCase.executar(null, descricao, usuarioId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome é vazio")
    void deveLancarExcecaoQuandoNomeVazio() {
        String nomeVazio = "   ";
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());
        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);

        assertThrows(IllegalArgumentException.class, () -> criarCategoriaMemeUseCase.executar(nomeVazio, descricao, usuarioId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome tem mais de 100 caracteres")
    void deveLancarExcecaoQuandoNomeMuitoLongo() {
        String nomeLongo = "a".repeat(101);
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());
        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);

        assertThrows(IllegalArgumentException.class, () -> criarCategoriaMemeUseCase.executar(nomeLongo, descricao, usuarioId));
    }

    @Test
    @DisplayName("Deve aceitar nome com exatamente 100 caracteres")
    void deveAceitarNomeCom100Caracteres() {
        String nomeValido = "a".repeat(100);
        CategoriaMeme categoriaEsperada = CategoriaMeme.criar(nomeValido, descricao, usuarioId);
        categoriaEsperada.setId(1L);

        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());

        when(categoriaMemeRepository.salvar(any(CategoriaMeme.class))).thenReturn(categoriaEsperada);
        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);

        CategoriaMeme resultado = criarCategoriaMemeUseCase.executar(nomeValido, descricao, usuarioId);

        assertNotNull(resultado);
        assertEquals(100, resultado.getNome().length());
        verify(categoriaMemeRepository, times(1)).salvar(any(CategoriaMeme.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando descrição é inválida")
    void deveLancarExcecaoQuandoDescricaoInvalida() {
        String descricaoInvalida = "abc";
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());
        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);

        assertThrows(IllegalArgumentException.class, () -> criarCategoriaMemeUseCase.executar(nome, descricaoInvalida, usuarioId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando descrição é nula")
    void deveLancarExcecaoQuandoDescricaoNula() {
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());
        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);

        assertThrows(IllegalArgumentException.class, () -> criarCategoriaMemeUseCase.executar(nome, null, usuarioId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando descrição é vazia")
    void deveLancarExcecaoQuandoDescricaoVazia() {
        String descricaoVazia = "   ";
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());
        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);

        assertThrows(IllegalArgumentException.class, () -> criarCategoriaMemeUseCase.executar(nome, descricaoVazia, usuarioId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando ID do usuário é nulo")
    void deveLancarExcecaoQuandoUsuarioIdNulo() {
        assertThrows(IllegalArgumentException.class, () -> criarCategoriaMemeUseCase.executar(nome, descricao, null));

        verify(usuarioClient, never()).buscarUsuarioPorId(anyLong());
        verify(categoriaMemeRepository, never()).salvar(any(CategoriaMeme.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando ID do usuário é zero")
    void deveLancarExcecaoQuandoUsuarioIdZero() {
        Long usuarioIdInvalido = 0L;
        when(usuarioClient.buscarUsuarioPorId(usuarioIdInvalido)).thenThrow(new RuntimeException("ID inválido"));

        assertThrows(UsuarioInvalidoException.class, () -> criarCategoriaMemeUseCase.executar(nome, descricao, usuarioIdInvalido));

        verify(usuarioClient, times(1)).buscarUsuarioPorId(usuarioIdInvalido);
        verify(categoriaMemeRepository, never()).salvar(any(CategoriaMeme.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando ID do usuário é negativo")
    void deveLancarExcecaoQuandoUsuarioIdNegativo() {
        Long usuarioIdInvalido = -1L;
        when(usuarioClient.buscarUsuarioPorId(usuarioIdInvalido)).thenThrow(new RuntimeException("ID inválido"));

        assertThrows(UsuarioInvalidoException.class, () -> criarCategoriaMemeUseCase.executar(nome, descricao, usuarioIdInvalido));

        verify(usuarioClient, times(1)).buscarUsuarioPorId(usuarioIdInvalido);
        verify(categoriaMemeRepository, never()).salvar(any(CategoriaMeme.class));
    }

    @Test
    @DisplayName("Deve criar categoria com nome mínimo válido - 3 caracteres")
    void deveCriarCategoriaComNomeMinimoValido() {
        String nomeMinimo = "ABC";
        CategoriaMeme categoriaEsperada = CategoriaMeme.criar(nomeMinimo, descricao, usuarioId);
        categoriaEsperada.setId(1L);

        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());

        when(categoriaMemeRepository.salvar(any(CategoriaMeme.class))).thenReturn(categoriaEsperada);
        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);

        CategoriaMeme resultado = criarCategoriaMemeUseCase.executar(nomeMinimo, descricao, usuarioId);

        assertNotNull(resultado);
        assertEquals(nomeMinimo, resultado.getNome());
        verify(categoriaMemeRepository, times(1)).salvar(any(CategoriaMeme.class));
    }

    @Test
    @DisplayName("Deve criar categoria com descrição mínima válida - 5 caracteres")
    void deveCriarCategoriaComDescricaoMinimaValida() {
        String descricaoMinima = "abcde";
        CategoriaMeme categoriaEsperada = CategoriaMeme.criar(nome, descricaoMinima, usuarioId);
        categoriaEsperada.setId(1L);

        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());

        when(categoriaMemeRepository.salvar(any(CategoriaMeme.class))).thenReturn(categoriaEsperada);
        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);

        CategoriaMeme resultado = criarCategoriaMemeUseCase.executar(nome, descricaoMinima, usuarioId);

        assertNotNull(resultado);
        assertEquals(descricaoMinima, resultado.getDescricao());
        verify(categoriaMemeRepository, times(1)).salvar(any(CategoriaMeme.class));
    }
}
