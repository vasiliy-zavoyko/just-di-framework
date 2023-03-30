package ru.zavoyko.framework.di.exceptions;

public abstract class DIFrameworkException extends RuntimeException {

    public DIFrameworkException(String message) {
        super(message);
    }

    public DIFrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
