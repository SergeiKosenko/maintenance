package ru.service.maintenance.core.exceptions;

public class IncorrectTokenException extends RuntimeException {
    public IncorrectTokenException(String message) {
        super(message);
    }
}
