package ru.zavoyko.framework.di.properties;

import ru.zavoyko.framework.di.exceptions.DIFrameworkException;

/**
 * The exception for the properties.
 */
public class PropertiesException extends DIFrameworkException {

    public PropertiesException(String message) {
        super(message);
    }

    public PropertiesException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertiesException(Throwable cause) {
        super(cause);
    }

    public PropertiesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
