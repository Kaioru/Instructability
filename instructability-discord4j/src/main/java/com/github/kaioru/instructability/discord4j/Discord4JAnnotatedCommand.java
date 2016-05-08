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

	String perm() default Defaults.PERMISSION;

	boolean allowPrivateMessage() default Defaults.ALLOW_PRIVATE_MESSAGE;

	boolean removeTriggerMessage() default Defaults.REMOVE_TRIGGER_MESSAGE;

}
