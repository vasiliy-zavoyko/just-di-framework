package ru.zavoyko.framework.di.exceptions;

public class DIComponentDefinitionException extends DIFrameworkException {

    public DIComponentDefinitionException(String message) {
        super(message);
    }

    public DIComponentDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DIComponentDefinitionException(Throwable cause) {
        super(cause);
    }

    public DIComponentDefinitionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
