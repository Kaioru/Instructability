package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.Defaults;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Discord4JAnnotatedCommand {

	String name();

	String desc() default Defaults.DESCRIPTION;

	String perm() default "";

	boolean allowPrivateMessage() default false;

	boolean removeTriggerMessage() default true;

}
