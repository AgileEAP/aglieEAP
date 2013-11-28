package com.agileEAP.workflow.engine;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({TYPE})
@Retention(RUNTIME)
public @interface EventUUID
{
    /**
     * the work flow event unique id 
     */
    String uuid() default "";
}