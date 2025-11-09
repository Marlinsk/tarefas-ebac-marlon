package br.com.ebac.animal_service.interfaces.exception;

import br.com.ebac.animal_service.usecase.exception.AnimalNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        when(request.getRequestURI()).thenReturn("/api/animais");
    }

    @Test
    void deveRetornarNotFoundQuandoAnimalNaoEncontrado() {
        AnimalNotFoundException exception = new AnimalNotFoundException("Animal com ID 999 não encontrado");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleAnimalNotFound(exception, request);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().status());
        assertEquals("Not Found", response.getBody().error());
        assertEquals("Animal com ID 999 não encontrado", response.getBody().message());
        assertEquals("/api/animais", response.getBody().path());
    }

    @Test
    void deveRetornarBadRequestQuandoIllegalState() {
        IllegalStateException exception = new IllegalStateException("Animal já foi adotado");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleIllegalState(exception, request);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().status());
        assertEquals("Bad Request", response.getBody().error());
        assertEquals("Animal já foi adotado", response.getBody().message());
        assertEquals("/api/animais", response.getBody().path());
    }

    @Test
    void deveRetornarBadRequestQuandoErroDeValidacao() {
        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        FieldError fieldError1 = new FieldError("animal", "nomeProvisorio", "Nome provisório é obrigatório");
        FieldError fieldError2 = new FieldError("animal", "idadeEstimada", "Idade estimada é obrigatória");

        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError1, fieldError2));

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleValidationErrors(exception, request);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().status());
        assertEquals("Validation Error", response.getBody().error());
        assertEquals("Erro de validação nos campos", response.getBody().message());
        assertNotNull(response.getBody().fieldErrors());
        assertEquals(2, response.getBody().fieldErrors().size());
        assertEquals("nomeProvisorio", response.getBody().fieldErrors().get(0).field());
        assertEquals("Nome provisório é obrigatório", response.getBody().fieldErrors().get(0).message());
    }

    @Test
    void deveRetornarInternalServerErrorQuandoErroGenerico() {
        Exception exception = new Exception("Erro inesperado");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGenericError(exception, request);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().status());
        assertEquals("Internal Server Error", response.getBody().error());
        assertEquals("Ocorreu um erro interno no servidor", response.getBody().message());
        assertEquals("/api/animais", response.getBody().path());
    }

    @Test
    void deveIncluirTimestampNaRespostaDeErro() {
        AnimalNotFoundException exception = new AnimalNotFoundException("Animal não encontrado");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleAnimalNotFound(exception, request);

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().timestamp());
    }
}
