package ru.zavoyko.framework.di.inject.java;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface TypeToInject {

    boolean isLazy() default false;
    boolean isSingleton() default true;

}
