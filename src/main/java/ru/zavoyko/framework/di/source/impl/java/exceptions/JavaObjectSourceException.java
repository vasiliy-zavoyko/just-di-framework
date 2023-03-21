package ru.zavoyko.framework.di.source.impl.java.exceptions;

import ru.zavoyko.framework.di.exceptions.DIFrameworkException;

/**
 * The exception for the Java configuration.
 */
public abstract class JavaObjectSourceException extends DIFrameworkException {

    protected JavaObjectSourceException(String message) {
        super(message);
    }

    protected JavaObjectSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    protected JavaObjectSourceException(Throwable cause) {
        super(cause);
    }

    protected JavaObjectSourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
