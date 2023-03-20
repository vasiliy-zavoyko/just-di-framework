package ru.zavoyko.framework.di.config.java;

import ru.zavoyko.framework.di.exceptions.DIContainerException;

/**
 * The exception for the Java configuration.
 */
public class JavaConfigException extends DIContainerException {

    public JavaConfigException(String message) {
        super(message);
    }

}
