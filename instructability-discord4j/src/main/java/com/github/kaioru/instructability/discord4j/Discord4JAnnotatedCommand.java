package com.github.kaioru.instructability.discord4j;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Discord4JAnnotatedCommand {

	String name();

	String desc() default "No description";

	boolean allowPrivateMessage() default false;

	boolean removeTriggerMessage() default true;

}
