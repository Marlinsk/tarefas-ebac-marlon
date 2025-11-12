package br.com.ebac.memelandia.memes.application.usecase;

import br.com.ebac.memelandia.memes.domain.entity.Meme;
import br.com.ebac.memelandia.memes.domain.exception.MemeNaoEncontradoException;
import br.com.ebac.memelandia.memes.domain.repository.MemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do BuscarMemeUseCase")
class BuscarMemeUseCaseTest {

    @Mock
    private MemeRepository memeRepository;

    @InjectMocks
    private BuscarMemeUseCase buscarMemeUseCase;

    private Meme meme1;
    private Meme meme2;
    private Meme meme3;

    @BeforeEach
    void setUp() {
        meme1 = new Meme(
                1L,
                "Meme 1",
                "Descrição do meme 1",
                "data:image/png;base64,abc123",
                LocalDate.now(),
                1L,
                1L
        );

        meme2 = new Meme(
                2L,
                "Meme 2",
                "Descrição do meme 2",
                "data:image/png;base64,def456",
                LocalDate.now(),
                1L,
                2L
        );

        meme3 = new Meme(
                3L,
                "Meme 3",
                "Descrição do meme 3",
                "http://example.com/meme3.jpg",
                LocalDate.now(),
                2L,
                1L
        );
    }

    @Test
    @DisplayName("Deve buscar meme por ID com sucesso")
    void deveBuscarMemePorIdComSucesso() {
        Long memeId = 1L;
        when(memeRepository.buscarPorId(memeId)).thenReturn(Optional.of(meme1));

        Meme resultado = buscarMemeUseCase.buscarPorId(memeId);

        assertNotNull(resultado);
        assertEquals(memeId, resultado.getId());
        assertEquals("Meme 1", resultado.getNome());
        assertEquals("Descrição do meme 1", resultado.getDescricao());
        verify(memeRepository, times(1)).buscarPorId(memeId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando meme não existe")
    void deveLancarExcecaoQuandoMemeNaoExiste() {
        Long memeId = 999L;
        when(memeRepository.buscarPorId(memeId)).thenReturn(Optional.empty());

        MemeNaoEncontradoException exception = assertThrows(MemeNaoEncontradoException.class, () -> buscarMemeUseCase.buscarPorId(memeId));

        verify(memeRepository, times(1)).buscarPorId(memeId);
    }

    @Test
    @DisplayName("Deve buscar todos os memes com sucesso")
    void deveBuscarTodosMemesComSucesso() {
        List<Meme> memesEsperados = Arrays.asList(meme1, meme2, meme3);
        when(memeRepository.buscarTodos()).thenReturn(memesEsperados);

        List<Meme> resultado = buscarMemeUseCase.buscarTodos();

        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        assertTrue(resultado.contains(meme1));
        assertTrue(resultado.contains(meme2));
        assertTrue(resultado.contains(meme3));
        verify(memeRepository, times(1)).buscarTodos();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não existem memes")
    void deveRetornarListaVaziaQuandoNaoExistemMemes() {
        when(memeRepository.buscarTodos()).thenReturn(Collections.emptyList());

        List<Meme> resultado = buscarMemeUseCase.buscarTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(memeRepository, times(1)).buscarTodos();
    }

    @Test
    @DisplayName("Deve buscar memes por usuário ID com sucesso")
    void deveBuscarMemesPorUsuarioIdComSucesso() {
        Long usuarioId = 1L;
        List<Meme> memesEsperados = Arrays.asList(meme1, meme2);
        when(memeRepository.buscarPorUsuarioId(usuarioId)).thenReturn(memesEsperados);

        List<Meme> resultado = buscarMemeUseCase.buscarPorUsuarioId(usuarioId);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(meme1));
        assertTrue(resultado.contains(meme2));
        assertFalse(resultado.contains(meme3));
        verify(memeRepository, times(1)).buscarPorUsuarioId(usuarioId);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando usuário não possui memes")
    void deveRetornarListaVaziaQuandoUsuarioNaoPossuiMemes() {
        Long usuarioId = 999L;
        when(memeRepository.buscarPorUsuarioId(usuarioId)).thenReturn(Collections.emptyList());

        List<Meme> resultado = buscarMemeUseCase.buscarPorUsuarioId(usuarioId);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(memeRepository, times(1)).buscarPorUsuarioId(usuarioId);
    }

    @Test
    @DisplayName("Deve buscar memes por categoria ID com sucesso")
    void deveBuscarMemesPorCategoriaIdComSucesso() {
        Long categoriaId = 1L;
        List<Meme> memesEsperados = Arrays.asList(meme1, meme3);
        when(memeRepository.buscarPorCategoriaId(categoriaId)).thenReturn(memesEsperados);

        List<Meme> resultado = buscarMemeUseCase.buscarPorCategoriaId(categoriaId);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(meme1));
        assertTrue(resultado.contains(meme3));
        assertFalse(resultado.contains(meme2));
        verify(memeRepository, times(1)).buscarPorCategoriaId(categoriaId);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando categoria não possui memes")
    void deveRetornarListaVaziaQuandoCategoriaNaoPossuiMemes() {
        Long categoriaId = 999L;
        when(memeRepository.buscarPorCategoriaId(categoriaId)).thenReturn(Collections.emptyList());

        List<Meme> resultado = buscarMemeUseCase.buscarPorCategoriaId(categoriaId);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(memeRepository, times(1)).buscarPorCategoriaId(categoriaId);
    }

    @Test
    @DisplayName("Deve buscar múltiplos memes do mesmo usuário e mesma categoria")
    void deveBuscarMultiplosMemesDoMesmoUsuarioEMesmaCategoria() {
        Long usuarioId = 1L;
        Long categoriaId = 1L;

        when(memeRepository.buscarPorUsuarioId(usuarioId)).thenReturn(Arrays.asList(meme1, meme2));
        when(memeRepository.buscarPorCategoriaId(categoriaId)).thenReturn(Arrays.asList(meme1, meme3));

        List<Meme> memesPorUsuario = buscarMemeUseCase.buscarPorUsuarioId(usuarioId);
        List<Meme> memesPorCategoria = buscarMemeUseCase.buscarPorCategoriaId(categoriaId);

        assertNotNull(memesPorUsuario);
        assertNotNull(memesPorCategoria);
        assertEquals(2, memesPorUsuario.size());
        assertEquals(2, memesPorCategoria.size());

        assertTrue(memesPorUsuario.contains(meme1));
        assertTrue(memesPorCategoria.contains(meme1));

        verify(memeRepository, times(1)).buscarPorUsuarioId(usuarioId);
        verify(memeRepository, times(1)).buscarPorCategoriaId(categoriaId);
    }
}
