package ru.zavoyko.framework.di.popertis;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectFromProperty {

     String value() default "";

}
