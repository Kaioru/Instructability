package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.Defaults;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to represent a annotated Discord4J command
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Discord4JAnnotatedCommand {

	/**
	 * @return Name of the command.
	 */
	String name();

	/**
	 * @return Description of the command.
	 */
	String desc() default Defaults.DESCRIPTION;

	/**
	 * @return Permission of the command.
	 */
	String perm() default Defaults.PERMISSION;

	/**
	 * @return Whether to allow private messages for this command.
	 */
	boolean allowPrivateMessage() default Defaults.ALLOW_PRIVATE_MESSAGE;

	/**
	 * @return Whether to remove the message after command execution.
	 */
	boolean removeTriggerMessage() default Defaults.REMOVE_TRIGGER_MESSAGE;

}
