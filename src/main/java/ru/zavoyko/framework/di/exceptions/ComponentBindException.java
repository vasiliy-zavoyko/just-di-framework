package ru.zavoyko.framework.di.exceptions;

public class ComponentBindException extends DIFrameworkException {

    public ComponentBindException(String message) {
        super(message);
    }

    protected ComponentBindException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ComponentBindException(Throwable cause) {
        super(cause);
    }

    protected ComponentBindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
