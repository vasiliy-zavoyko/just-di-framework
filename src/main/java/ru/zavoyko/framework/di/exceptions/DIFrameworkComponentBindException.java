package ru.zavoyko.framework.di.exceptions;

public class DIFrameworkComponentBindException extends DIFrameworkException {

    public DIFrameworkComponentBindException(String message) {
        super(message);
    }

    public DIFrameworkComponentBindException(String message, Throwable cause) {
        super(message, cause);
    }

    public DIFrameworkComponentBindException(Throwable cause) {
        super(cause);
    }

    public DIFrameworkComponentBindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
