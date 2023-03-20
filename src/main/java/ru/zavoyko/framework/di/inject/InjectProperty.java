package ru.zavoyko.framework.di.inject;


import java.lang.annotation.Retention;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface InjectProperty {

    String value() default "";

}
