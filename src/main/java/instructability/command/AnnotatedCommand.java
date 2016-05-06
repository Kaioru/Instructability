package instructability.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotatedCommand {

	String name();

	String desc() default "No description";

	boolean removeTriggerMessage() default true;

	boolean allowPrivateMessage() default false;

}
