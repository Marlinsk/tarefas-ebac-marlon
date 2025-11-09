package br.com.ebac.animal_service.usecase.exception;

public class AnimalNotFoundException extends RuntimeException {

    public AnimalNotFoundException(String message) {
        super(message);
    }
}
