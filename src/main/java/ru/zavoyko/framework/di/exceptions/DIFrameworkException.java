package ru.zavoyko.framework.di.exceptions;

/**
 * The base exception for the DI container.
 */
public abstract class DIFrameworkException extends RuntimeException {

    protected DIFrameworkException(String message) {
        super(message);
    }

    protected DIFrameworkException(String message, Throwable cause) {
        super(message, cause);
    }

    protected DIFrameworkException(Throwable cause) {
        super(cause);
    }

    protected DIFrameworkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
