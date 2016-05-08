package com.github.kaioru.instructability.command;

import com.github.kaioru.instructability.Instructables;

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

	default void registerCommand(Command cmd) {
		getCommands().add(cmd);
		Instructables.LOGGER.info(
				"Registered command '{}' to '{}'",
				cmd.getName(),
				getName()
		);
	}

	default void unregisterCommand(Command cmd) {
		getCommands().remove(cmd);
		Instructables.LOGGER.info(
				"Unregistered command '{}' from '{}'",
				cmd.getName(),
				getName()
		);
	}

	List<CommandVerifier> getPreVerifiers();

	List<CommandVerifier> getPostVerifiers();

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
