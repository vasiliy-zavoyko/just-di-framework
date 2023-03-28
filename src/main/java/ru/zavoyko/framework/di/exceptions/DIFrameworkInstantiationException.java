package ru.zavoyko.framework.di.exceptions;

public class DIFrameworkInstantiationException extends DIFrameworkException {

        public DIFrameworkInstantiationException(String message) {
            super(message);
        }

        public DIFrameworkInstantiationException(String message, Throwable cause) {
            super(message, cause);
        }

        public DIFrameworkInstantiationException(Throwable cause) {
            super(cause);
        }

}
