package ru.zavoyko.framework.di.exceptions;

public class DiFrameworkInvokeMethodException extends DIFrameworkException {
    public DiFrameworkInvokeMethodException(String message) {
        super(message);
    }

    public DiFrameworkInvokeMethodException(String message, Throwable cause) {
        super(message, cause);
    }
}
