package ru.zavoyko.framework.di.source.impl.java.exceptions;

public class JavaObjectSourceInstanceCreationException extends JavaObjectSourceException {

    public JavaObjectSourceInstanceCreationException(String message) {
        super(message);
    }

    public JavaObjectSourceInstanceCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JavaObjectSourceInstanceCreationException(Throwable cause) {
        super(cause);
    }

    public JavaObjectSourceInstanceCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
