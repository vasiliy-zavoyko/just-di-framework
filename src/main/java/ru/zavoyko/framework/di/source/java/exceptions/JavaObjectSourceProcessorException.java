package ru.zavoyko.framework.di.source.java.exceptions;

public class JavaObjectSourceProcessorException extends JavaObjectSourceException {

    public JavaObjectSourceProcessorException(String message) {
        super(message);
    }

    public JavaObjectSourceProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

    public JavaObjectSourceProcessorException(Throwable cause) {
        super(cause);
    }

    public JavaObjectSourceProcessorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
