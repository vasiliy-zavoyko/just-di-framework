package ru.zavoyko.framework.di;

public interface ActionProcessor {

    Object process(Context context, Object bean);

}
