package ru.service.maintenance.core.exceptions;

public class WaitingConfirmException extends RuntimeException {
    public WaitingConfirmException(String message) {
        super(message);
    }
}
