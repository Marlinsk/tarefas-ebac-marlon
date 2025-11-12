package br.com.ebac.memelandia.categoria.application.usecase;

import br.com.ebac.memelandia.categoria.domain.entities.CategoriaMeme;
import br.com.ebac.memelandia.categoria.domain.exception.CategoriaNaoEncontradaException;
import br.com.ebac.memelandia.categoria.domain.repository.CategoriaMemeRepository;
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
@DisplayName("Testes do BuscarCategoriaMemeUseCase")
class BuscarCategoriaMemeUseCaseTest {

    @Mock
    private CategoriaMemeRepository categoriaMemeRepository;

    @InjectMocks
    private BuscarCategoriaMemeUseCase buscarCategoriaMemeUseCase;

    private CategoriaMeme categoria1;
    private CategoriaMeme categoria2;
    private CategoriaMeme categoria3;

    @BeforeEach
    void setUp() {
        categoria1 = new CategoriaMeme(1L, "Categoria 1", "Descrição da categoria 1", LocalDate.now(), 1L);
        categoria2 = new CategoriaMeme(2L, "Categoria 2", "Descrição da categoria 2", LocalDate.now(), 1L);
        categoria3 = new CategoriaMeme(3L, "Categoria 3", "Descrição da categoria 3", LocalDate.now(), 2L);
    }

    @Test
    @DisplayName("Deve buscar categoria por ID com sucesso")
    void deveBuscarCategoriaPorIdComSucesso() {
        Long categoriaId = 1L;
        when(categoriaMemeRepository.buscarPorId(categoriaId)).thenReturn(Optional.of(categoria1));

        CategoriaMeme resultado = buscarCategoriaMemeUseCase.buscarPorId(categoriaId);

        assertNotNull(resultado);
        assertEquals(categoriaId, resultado.getId());
        assertEquals("Categoria 1", resultado.getNome());
        assertEquals("Descrição da categoria 1", resultado.getDescricao());
        assertEquals(1L, resultado.getUsuarioId());
        verify(categoriaMemeRepository, times(1)).buscarPorId(categoriaId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando categoria não existe")
    void deveLancarExcecaoQuandoCategoriaNaoExiste() {
        Long categoriaId = 999L;
        when(categoriaMemeRepository.buscarPorId(categoriaId)).thenReturn(Optional.empty());

        CategoriaNaoEncontradaException exception = assertThrows(CategoriaNaoEncontradaException.class, () -> buscarCategoriaMemeUseCase.buscarPorId(categoriaId));

        verify(categoriaMemeRepository, times(1)).buscarPorId(categoriaId);
    }

    @Test
    @DisplayName("Deve buscar todas as categorias com sucesso")
    void deveBuscarTodasCategoriasComSucesso() {
        List<CategoriaMeme> categoriasEsperadas = Arrays.asList(categoria1, categoria2, categoria3);
        when(categoriaMemeRepository.buscarTodas()).thenReturn(categoriasEsperadas);

        List<CategoriaMeme> resultado = buscarCategoriaMemeUseCase.buscarTodas();

        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        assertTrue(resultado.contains(categoria1));
        assertTrue(resultado.contains(categoria2));
        assertTrue(resultado.contains(categoria3));
        verify(categoriaMemeRepository, times(1)).buscarTodas();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não existem categorias")
    void deveRetornarListaVaziaQuandoNaoExistemCategorias() {
        when(categoriaMemeRepository.buscarTodas()).thenReturn(Collections.emptyList());

        List<CategoriaMeme> resultado = buscarCategoriaMemeUseCase.buscarTodas();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(categoriaMemeRepository, times(1)).buscarTodas();
    }

    @Test
    @DisplayName("Deve buscar categorias por usuário ID com sucesso")
    void deveBuscarCategoriasPorUsuarioIdComSucesso() {
        Long usuarioId = 1L;
        List<CategoriaMeme> categoriasEsperadas = Arrays.asList(categoria1, categoria2);
        when(categoriaMemeRepository.buscarPorUsuarioId(usuarioId)).thenReturn(categoriasEsperadas);

        List<CategoriaMeme> resultado = buscarCategoriaMemeUseCase.buscarPorUsuarioId(usuarioId);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(categoria1));
        assertTrue(resultado.contains(categoria2));
        assertFalse(resultado.contains(categoria3));
        verify(categoriaMemeRepository, times(1)).buscarPorUsuarioId(usuarioId);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando usuário não possui categorias")
    void deveRetornarListaVaziaQuandoUsuarioNaoPossuiCategorias() {
        Long usuarioId = 999L;
        when(categoriaMemeRepository.buscarPorUsuarioId(usuarioId)).thenReturn(Collections.emptyList());

        List<CategoriaMeme> resultado = buscarCategoriaMemeUseCase.buscarPorUsuarioId(usuarioId);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(categoriaMemeRepository, times(1)).buscarPorUsuarioId(usuarioId);
    }

    @Test
    @DisplayName("Deve buscar múltiplas categorias do mesmo usuário")
    void deveBuscarMultiplasCategoriasDoMesmoUsuario() {
        Long usuarioId = 1L;
        when(categoriaMemeRepository.buscarPorUsuarioId(usuarioId)).thenReturn(Arrays.asList(categoria1, categoria2));

        List<CategoriaMeme> resultado = buscarCategoriaMemeUseCase.buscarPorUsuarioId(usuarioId);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(usuarioId, resultado.get(0).getUsuarioId());
        assertEquals(usuarioId, resultado.get(1).getUsuarioId());
        verify(categoriaMemeRepository, times(1)).buscarPorUsuarioId(usuarioId);
    }

    @Test
    @DisplayName("Deve buscar categoria específica e verificar seus dados")
    void deveBuscarCategoriaEspecificaEVerificarDados() {
        Long categoriaId = 2L;
        when(categoriaMemeRepository.buscarPorId(categoriaId)).thenReturn(Optional.of(categoria2));

        CategoriaMeme resultado = buscarCategoriaMemeUseCase.buscarPorId(categoriaId);

        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        assertEquals("Categoria 2", resultado.getNome());
        assertEquals("Descrição da categoria 2", resultado.getDescricao());
        assertEquals(1L, resultado.getUsuarioId());
        assertNotNull(resultado.getDataCadastro());
        verify(categoriaMemeRepository, times(1)).buscarPorId(categoriaId);
    }

    @Test
    @DisplayName("Deve buscar todas as categorias e verificar ordem de retorno")
    void deveBuscarTodasCategoriasEVerificarOrdem() {
        List<CategoriaMeme> categoriasOrdenadas = Arrays.asList(categoria1, categoria2, categoria3);
        when(categoriaMemeRepository.buscarTodas()).thenReturn(categoriasOrdenadas);

        List<CategoriaMeme> resultado = buscarCategoriaMemeUseCase.buscarTodas();

        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals(2L, resultado.get(1).getId());
        assertEquals(3L, resultado.get(2).getId());
        verify(categoriaMemeRepository, times(1)).buscarTodas();
    }

    @Test
    @DisplayName("Deve buscar categorias por usuários diferentes")
    void deveBuscarCategoriasPorUsuariosDiferentes() {
        Long usuarioId1 = 1L;
        Long usuarioId2 = 2L;

        when(categoriaMemeRepository.buscarPorUsuarioId(usuarioId1)).thenReturn(Arrays.asList(categoria1, categoria2));
        when(categoriaMemeRepository.buscarPorUsuarioId(usuarioId2)).thenReturn(Collections.singletonList(categoria3));

        List<CategoriaMeme> resultadoUsuario1 = buscarCategoriaMemeUseCase.buscarPorUsuarioId(usuarioId1);
        List<CategoriaMeme> resultadoUsuario2 = buscarCategoriaMemeUseCase.buscarPorUsuarioId(usuarioId2);

        assertNotNull(resultadoUsuario1);
        assertNotNull(resultadoUsuario2);
        assertEquals(2, resultadoUsuario1.size());
        assertEquals(1, resultadoUsuario2.size());
        assertEquals(usuarioId1, resultadoUsuario1.get(0).getUsuarioId());
        assertEquals(usuarioId2, resultadoUsuario2.get(0).getUsuarioId());

        verify(categoriaMemeRepository, times(1)).buscarPorUsuarioId(usuarioId1);
        verify(categoriaMemeRepository, times(1)).buscarPorUsuarioId(usuarioId2);
    }

    @Test
    @DisplayName("Deve validar que categoria retornada não é nula")
    void deveValidarQueCategoriaNaoENula() {
        Long categoriaId = 1L;
        when(categoriaMemeRepository.buscarPorId(categoriaId)).thenReturn(Optional.of(categoria1));

        CategoriaMeme resultado = buscarCategoriaMemeUseCase.buscarPorId(categoriaId);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertNotNull(resultado.getNome());
        assertNotNull(resultado.getDescricao());
        assertNotNull(resultado.getDataCadastro());
        assertNotNull(resultado.getUsuarioId());
    }
}
