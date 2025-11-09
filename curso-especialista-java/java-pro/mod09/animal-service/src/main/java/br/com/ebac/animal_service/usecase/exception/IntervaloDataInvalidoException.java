package br.com.ebac.animal_service.usecase.exception;

public class IntervaloDataInvalidoException extends RuntimeException {
    public IntervaloDataInvalidoException(String message) {
        super(message);
    }
}
