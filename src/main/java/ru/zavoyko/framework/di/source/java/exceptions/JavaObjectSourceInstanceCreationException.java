package ru.zavoyko.framework.di.source.java.exceptions;

public class JavaObjectSourceInstanceCreationException extends JavaObjectSourceException {

    public JavaObjectSourceInstanceCreationException(String message) {
        super(message);
    }

    protected JavaObjectSourceInstanceCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    protected JavaObjectSourceInstanceCreationException(Throwable cause) {
        super(cause);
    }

    protected JavaObjectSourceInstanceCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
