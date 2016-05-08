package com.github.kaioru.instructability.command;

import com.github.kaioru.instructability.Instructables;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public interface Command extends CommandExecutor {

	String getName();

	String getDesc();

	String getPermission();

	List<String> getAliases();

	List<Command> getCommands();

	default List<Command> getCommand(String name) {
		return getCommands().stream()
				.filter(cmd -> cmd.getName().startsWith(name)
						|| cmd.getAliases().stream().anyMatch(s -> s.startsWith(name)))
				.collect(Collectors.toList());
	}

	default Command registerCommand(Command cmd) {
		getCommands().add(cmd);
		Instructables.LOGGER.info(
				"Registered command '{}' to '{}'",
				cmd.getName(),
				getName()
		);
		return this;
	}

	default Command unregisterCommand(Command cmd) {
		getCommands().remove(cmd);
		Instructables.LOGGER.info(
				"Unregistered command '{}' from '{}'",
				cmd.getName(),
				getName()
		);
		return this;
	}

	default Command registerCommands(Object object) throws InvocationTargetException, IllegalAccessException {
		for (Method method : object.getClass().getMethods()) {
			if (method.isAnnotationPresent(CommandReference.class))
				registerCommand((Command) method.invoke(object));
		}
		return this;
	}

	default Command unregisterCommands(Object object) throws InvocationTargetException, IllegalAccessException {
		for (Method method : object.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(CommandReference.class)) {
				Command cmd = (Command) method.invoke(object);

				getCommands().stream()
						.filter(c -> c.getName().equals(cmd.getName())
								&& c.getDesc().equals(cmd.getDesc()))
						.forEach(this::unregisterCommand);
			}
		}
		return this;
	}

	List<CommandVerifier> getPreVerifiers();

	List<CommandVerifier> getPostVerifiers();

	default boolean addHelperCommands() { return true; }

	default void registerPreVerifier(CommandVerifier verifier) {
		getPreVerifiers().add(verifier);
	}

	default void deregisterPreVerifier(CommandVerifier verifier) {
		getPreVerifiers().remove(verifier);
	}

	default void registerPostVerifier(CommandVerifier verifier) {
		getPostVerifiers().add(verifier);
	}

	default void deregisterPostVerifier(CommandVerifier verifier) {
		getPostVerifiers().remove(verifier);
	}

}
