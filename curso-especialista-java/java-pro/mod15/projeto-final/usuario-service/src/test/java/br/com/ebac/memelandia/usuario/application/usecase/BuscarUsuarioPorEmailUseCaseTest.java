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
@DisplayName("BuscarUsuarioPorEmailUseCase Tests")
public class BuscarUsuarioPorEmailUseCaseTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmailUseCase;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, "João Silva", "joao@example.com", LocalDate.now());
    }

    @Test
    @DisplayName("Deve buscar usuário por email com sucesso")
    void deveBuscarUsuarioPorEmailComSucesso() {
        when(usuarioRepository.findByEmail("joao@example.com")).thenReturn(Optional.of(usuario));

        UsuarioDTO resultado = buscarUsuarioPorEmailUseCase.executar("joao@example.com");

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@example.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).findByEmail("joao@example.com");
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado por email")
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {
        String emailInexistente = "inexistente@example.com";
        when(usuarioRepository.findByEmail(emailInexistente)).thenReturn(Optional.empty());

        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> buscarUsuarioPorEmailUseCase.executar(emailInexistente));

        assertTrue(exception.getMessage().contains(emailInexistente));
        verify(usuarioRepository, times(1)).findByEmail(emailInexistente);
    }

    @Test
    @DisplayName("Deve retornar DTO correto quando múltiplos usuários existem")
    void deveRetornarDTOCorretoQuandoMultiplosUsuariosExistem() {
        Usuario usuario2 = new Usuario(2L, "Maria Santos", "maria@example.com", LocalDate.now());

        when(usuarioRepository.findByEmail("joao@example.com")).thenReturn(Optional.of(usuario));
        when(usuarioRepository.findByEmail("maria@example.com")).thenReturn(Optional.of(usuario2));

        UsuarioDTO resultado1 = buscarUsuarioPorEmailUseCase.executar("joao@example.com");
        UsuarioDTO resultado2 = buscarUsuarioPorEmailUseCase.executar("maria@example.com");

        assertEquals(1L, resultado1.getId());
        assertEquals("João Silva", resultado1.getNome());
        assertEquals(2L, resultado2.getId());
        assertEquals("Maria Santos", resultado2.getNome());
    }

    @Test
    @DisplayName("Deve chamar repositório apenas uma vez")
    void deveChamarRepositorioApenasUmaVez() {
        when(usuarioRepository.findByEmail("joao@example.com")).thenReturn(Optional.of(usuario));
        buscarUsuarioPorEmailUseCase.executar("joao@example.com");
        verify(usuarioRepository, times(1)).findByEmail("joao@example.com");
    }

    @Test
    @DisplayName("Deve propagar exceção do repositório")
    void devePropagarExcecaoDoRepositorio() {
        when(usuarioRepository.findByEmail("joao@example.com")).thenThrow(new RuntimeException("Erro no banco de dados"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> buscarUsuarioPorEmailUseCase.executar("joao@example.com"));
        assertEquals("Erro no banco de dados", exception.getMessage());
        verify(usuarioRepository, times(1)).findByEmail("joao@example.com");
    }

    @Test
    @DisplayName("Deve retornar DTO com data de cadastro")
    void deveRetornarDTOComDataCadastro() {
        LocalDate dataEsperada = LocalDate.of(2024, 1, 15);
        Usuario usuarioComData = new Usuario(1L, "João Silva", "joao@example.com", dataEsperada);
        when(usuarioRepository.findByEmail("joao@example.com")).thenReturn(Optional.of(usuarioComData));

        UsuarioDTO resultado = buscarUsuarioPorEmailUseCase.executar("joao@example.com");

        assertEquals(dataEsperada, resultado.getDataCadastro());
    }
}
