package ru.zavoyko.framework.di.exceptions;

/**
 * The base exception for the DI container.
 */
public abstract class DIContainerException extends RuntimeException {

    protected DIContainerException(String message) {
        super(message);
    }

    protected DIContainerException(String message, Throwable cause) {
        super(message, cause);
    }

    protected DIContainerException(Throwable cause) {
        super(cause);
    }

    protected DIContainerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
