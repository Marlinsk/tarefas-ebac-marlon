package br.com.ebac.animal_service.interfaces.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path, List<FieldError> fieldErrors) {
    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this(timestamp, status, error, message, path, null);
    }

    public record FieldError(String field, String message) {
    }
}
