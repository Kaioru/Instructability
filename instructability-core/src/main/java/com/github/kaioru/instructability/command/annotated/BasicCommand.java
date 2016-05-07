package com.github.kaioru.instructability.command.annotated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BasicCommand {

	String name();

	String desc() default "No description";

	boolean removeTriggerMessage() default true;

	boolean allowPrivateMessage() default false;

	int parameterCount() default 1;

}
