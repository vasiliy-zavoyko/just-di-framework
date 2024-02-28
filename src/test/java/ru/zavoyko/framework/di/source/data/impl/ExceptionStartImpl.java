package ru.zavoyko.framework.di.source.data.impl;

import ru.zavoyko.framework.di.annotations.ExceptionInterceptor;
import ru.zavoyko.framework.di.annotations.TypeToInject;
import ru.zavoyko.framework.di.exception.DIException;

@TypeToInject
public class ExceptionStartImpl {

    @ExceptionInterceptor
    public void hi() {
        throw new DIException("Hi");
    }
}
