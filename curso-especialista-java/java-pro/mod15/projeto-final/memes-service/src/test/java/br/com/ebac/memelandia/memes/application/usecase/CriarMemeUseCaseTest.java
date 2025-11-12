package br.com.ebac.memelandia.memes.application.usecase;

import br.com.ebac.memelandia.memes.domain.entity.Meme;
import br.com.ebac.memelandia.memes.domain.exception.CategoriaInvalidaException;
import br.com.ebac.memelandia.memes.domain.exception.UsuarioInvalidoException;
import br.com.ebac.memelandia.memes.domain.repository.MemeRepository;
import br.com.ebac.memelandia.memes.infrastructure.client.CategoriaClient;
import br.com.ebac.memelandia.memes.infrastructure.client.CategoriaResponse;
import br.com.ebac.memelandia.memes.infrastructure.client.UsuarioClient;
import br.com.ebac.memelandia.memes.infrastructure.client.UsuarioResponse;
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
@DisplayName("Testes do CriarMemeUseCase")
class CriarMemeUseCaseTest {

    @Mock
    private MemeRepository memeRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private CategoriaClient categoriaClient;

    @InjectMocks
    private CriarMemeUseCase criarMemeUseCase;

    private String nome;
    private String descricao;
    private String dataUrl;
    private Long usuarioId;
    private Long categoriaId;

    @BeforeEach
    void setUp() {
        nome = "Meme Teste";
        descricao = "Descrição do meme teste";
        dataUrl = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA";
        usuarioId = 1L;
        categoriaId = 1L;
    }

    @Test
    @DisplayName("Deve criar meme com sucesso quando usuário e categoria são válidos")
    void deveCriarMemeComSucesso() {
        Meme memeEsperado = Meme.criar(nome, descricao, dataUrl, usuarioId, categoriaId);
        memeEsperado.setId(1L);

        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());
        CategoriaResponse categoriaResponse = new CategoriaResponse(categoriaId, "Categoria Teste", "Descricao", LocalDate.now(), usuarioId);

        when(memeRepository.salvar(any(Meme.class))).thenReturn(memeEsperado);
        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);
        when(categoriaClient.buscarCategoriaPorId(categoriaId)).thenReturn(categoriaResponse);

        Meme resultado = criarMemeUseCase.executar(nome, descricao, dataUrl, usuarioId, categoriaId);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(nome, resultado.getNome());
        assertEquals(descricao, resultado.getDescricao());
        assertEquals(dataUrl, resultado.getDataUrl());
        assertEquals(usuarioId, resultado.getUsuarioId());
        assertEquals(categoriaId, resultado.getCategoriaId());
        assertNotNull(resultado.getDataCadastro());

        verify(usuarioClient, times(1)).buscarUsuarioPorId(usuarioId);
        verify(categoriaClient, times(1)).buscarCategoriaPorId(categoriaId);
        verify(memeRepository, times(1)).salvar(any(Meme.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não existe")
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        doThrow(new RuntimeException("Usuário não encontrado")).when(usuarioClient).buscarUsuarioPorId(usuarioId);

        assertThrows(UsuarioInvalidoException.class, () -> criarMemeUseCase.executar(nome, descricao, dataUrl, usuarioId, categoriaId));

        verify(usuarioClient, times(1)).buscarUsuarioPorId(usuarioId);
        verify(categoriaClient, never()).buscarCategoriaPorId(anyLong());
        verify(memeRepository, never()).salvar(any(Meme.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando categoria não existe")
    void deveLancarExcecaoQuandoCategoriaNaoExiste() {
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());
        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);
        when(categoriaClient.buscarCategoriaPorId(categoriaId)).thenThrow(new RuntimeException("Categoria não encontrada"));

        assertThrows(CategoriaInvalidaException.class, () -> criarMemeUseCase.executar(nome, descricao, dataUrl, usuarioId, categoriaId));

        verify(usuarioClient, times(1)).buscarUsuarioPorId(usuarioId);
        verify(categoriaClient, times(1)).buscarCategoriaPorId(categoriaId);
        verify(memeRepository, never()).salvar(any(Meme.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome é inválido")
    void deveLancarExcecaoQuandoNomeInvalido() {
        String nomeInvalido = "ab";
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());
        CategoriaResponse categoriaResponse = new CategoriaResponse(categoriaId, "Categoria Teste", "Descricao", LocalDate.now(), usuarioId);

        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);
        when(categoriaClient.buscarCategoriaPorId(categoriaId)).thenReturn(categoriaResponse);

        assertThrows(IllegalArgumentException.class, () -> criarMemeUseCase.executar(nomeInvalido, descricao, dataUrl, usuarioId, categoriaId));

        verify(usuarioClient, times(1)).buscarUsuarioPorId(usuarioId);
        verify(categoriaClient, times(1)).buscarCategoriaPorId(categoriaId);
        verify(memeRepository, never()).salvar(any(Meme.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome é nulo")
    void deveLancarExcecaoQuandoNomeNulo() {
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());
        CategoriaResponse categoriaResponse = new CategoriaResponse(categoriaId, "Categoria Teste", "Descricao", LocalDate.now(), usuarioId);

        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);
        when(categoriaClient.buscarCategoriaPorId(categoriaId)).thenReturn(categoriaResponse);

        assertThrows(IllegalArgumentException.class, () -> criarMemeUseCase.executar(null, descricao, dataUrl, usuarioId, categoriaId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando descrição é inválida")
    void deveLancarExcecaoQuandoDescricaoInvalida() {
        String descricaoInvalida = "abc";
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());
        CategoriaResponse categoriaResponse = new CategoriaResponse(categoriaId, "Categoria Teste", "Descricao", LocalDate.now(), usuarioId);

        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);
        when(categoriaClient.buscarCategoriaPorId(categoriaId)).thenReturn(categoriaResponse);

        assertThrows(IllegalArgumentException.class, () -> criarMemeUseCase.executar(nome, descricaoInvalida, dataUrl, usuarioId, categoriaId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando data URL é inválida")
    void deveLancarExcecaoQuandoDataUrlInvalida() {
        String dataUrlInvalida = "invalid-url";
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());
        CategoriaResponse categoriaResponse = new CategoriaResponse(categoriaId, "Categoria Teste", "Descricao", LocalDate.now(), usuarioId);

        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);
        when(categoriaClient.buscarCategoriaPorId(categoriaId)).thenReturn(categoriaResponse);

        assertThrows(IllegalArgumentException.class, () -> criarMemeUseCase.executar(nome, descricao, dataUrlInvalida, usuarioId, categoriaId));
    }

    @Test
    @DisplayName("Deve aceitar URL HTTP válida")
    void deveAceitarUrlHttpValida() {
        String httpUrl = "http://example.com/meme.jpg";
        Meme memeEsperado = Meme.criar(nome, descricao, httpUrl, usuarioId, categoriaId);
        memeEsperado.setId(1L);

        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());
        CategoriaResponse categoriaResponse = new CategoriaResponse(categoriaId, "Categoria Teste", "Descricao", LocalDate.now(), usuarioId);

        when(memeRepository.salvar(any(Meme.class))).thenReturn(memeEsperado);
        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);
        when(categoriaClient.buscarCategoriaPorId(categoriaId)).thenReturn(categoriaResponse);

        Meme resultado = criarMemeUseCase.executar(nome, descricao, httpUrl, usuarioId, categoriaId);

        assertNotNull(resultado);
        assertEquals(httpUrl, resultado.getDataUrl());
        verify(memeRepository, times(1)).salvar(any(Meme.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando ID do usuário é inválido")
    void deveLancarExcecaoQuandoUsuarioIdInvalido() {
        Long usuarioIdInvalido = -1L;
        when(usuarioClient.buscarUsuarioPorId(usuarioIdInvalido)).thenThrow(new RuntimeException("ID inválido"));

        assertThrows(UsuarioInvalidoException.class, () -> criarMemeUseCase.executar(nome, descricao, dataUrl, usuarioIdInvalido, categoriaId));

        verify(usuarioClient, times(1)).buscarUsuarioPorId(usuarioIdInvalido);
        verify(categoriaClient, never()).buscarCategoriaPorId(anyLong());
        verify(memeRepository, never()).salvar(any(Meme.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando ID da categoria é inválido")
    void deveLancarExcecaoQuandoCategoriaIdInvalido() {
        Long categoriaIdInvalido = 0L;
        UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioId, "Usuario Teste", "teste@email.com", LocalDate.now());
        when(usuarioClient.buscarUsuarioPorId(usuarioId)).thenReturn(usuarioResponse);
        when(categoriaClient.buscarCategoriaPorId(categoriaIdInvalido)).thenThrow(new RuntimeException("ID inválido"));

        assertThrows(CategoriaInvalidaException.class, () -> criarMemeUseCase.executar(nome, descricao, dataUrl, usuarioId, categoriaIdInvalido));

        verify(usuarioClient, times(1)).buscarUsuarioPorId(usuarioId);
        verify(categoriaClient, times(1)).buscarCategoriaPorId(categoriaIdInvalido);
        verify(memeRepository, never()).salvar(any(Meme.class));
    }
}
