package ru.zavoyko.framework.di.exceptions;

public abstract class DIComponentSourceException extends DIFrameworkException {

    protected DIComponentSourceException(String message) {
        super(message);
    }

    protected DIComponentSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    protected DIComponentSourceException(Throwable cause) {
        super(cause);
    }

    protected DIComponentSourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
