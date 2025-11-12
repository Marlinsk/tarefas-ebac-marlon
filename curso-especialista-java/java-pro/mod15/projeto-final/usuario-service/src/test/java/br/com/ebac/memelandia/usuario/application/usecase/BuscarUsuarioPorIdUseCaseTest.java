package br.com.ebac.memelandia.usuario.application.usecase;

import br.com.ebac.memelandia.usuario.application.dto.UsuarioDTO;
import br.com.ebac.memelandia.usuario.domain.entity.Usuario;
import br.com.ebac.memelandia.usuario.domain.exception.UsuarioNaoEncontradoException;
import br.com.ebac.memelandia.usuario.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BuscarUsuarioPorIdUseCase Tests")
public class BuscarUsuarioPorIdUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, "João Silva", "joao@example.com", LocalDate.now());
    }

    @Test
    @DisplayName("Deve buscar usuário por ID com sucesso")
    void deveBuscarUsuarioPorIdComSucesso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        UsuarioDTO resultado = buscarUsuarioPorIdUseCase.executar(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@example.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {
        Long idInexistente = 999L;
        when(usuarioRepository.findById(idInexistente)).thenReturn(Optional.empty());

        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> buscarUsuarioPorIdUseCase.executar(idInexistente));

        assertTrue(exception.getMessage().contains("999"));
        verify(usuarioRepository, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Deve retornar DTO correto quando múltiplos usuários existem")
    void deveRetornarDTOCorretoQuandoMultiplosUsuariosExistem() {
        Usuario usuario2 = new Usuario(2L, "Maria Santos", "maria@example.com", LocalDate.now());

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario2));

        UsuarioDTO resultado1 = buscarUsuarioPorIdUseCase.executar(1L);
        UsuarioDTO resultado2 = buscarUsuarioPorIdUseCase.executar(2L);

        assertEquals(1L, resultado1.getId());
        assertEquals("João Silva", resultado1.getNome());
        assertEquals(2L, resultado2.getId());
        assertEquals("Maria Santos", resultado2.getNome());
    }

    @Test
    @DisplayName("Deve chamar repositório apenas uma vez")
    void deveChamarRepositorioApenasUmaVez() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        buscarUsuarioPorIdUseCase.executar(1L);
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve propagar exceção do repositório")
    void devePropagarExcecaoDoRepositorio() {
        when(usuarioRepository.findById(1L)).thenThrow(new RuntimeException("Erro no banco de dados"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> buscarUsuarioPorIdUseCase.executar(1L));
        assertEquals("Erro no banco de dados", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar DTO com data de cadastro")
    void deveRetornarDTOComDataCadastro() {
        LocalDate dataEsperada = LocalDate.of(2024, 1, 15);
        Usuario usuarioComData = new Usuario(1L, "João Silva", "joao@example.com", dataEsperada);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioComData));

        UsuarioDTO resultado = buscarUsuarioPorIdUseCase.executar(1L);

        assertEquals(dataEsperada, resultado.getDataCadastro());
    }
}
