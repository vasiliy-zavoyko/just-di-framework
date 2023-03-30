package ru.zavoyko.framework.di.inject;


import javax.annotation.processing.Generated;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD })
public @interface InjectProperty {

    String value() default "";

}
