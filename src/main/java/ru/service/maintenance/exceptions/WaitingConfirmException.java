package ru.service.maintenance.exceptions;

public class WaitingConfirmException extends RuntimeException {
    public WaitingConfirmException(String message) {
        super(message);
    }
}
