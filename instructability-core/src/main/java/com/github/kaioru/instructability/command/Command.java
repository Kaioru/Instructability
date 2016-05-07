package com.github.kaioru.instructability.command;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface Command extends CommandExecutable {

	String getName();

	String getDesc();

	List<String> getAliases();

	List<Command> getCommands();

	CommandExecutable getExecutable();

	default boolean removeTriggerMessage() {
		return true;
	}

	default boolean allowPrivateMessage() { return false; }

	default Optional<Command> getCommand(String name) {
		return getCommands().stream()
				.filter(cmd -> cmd.getName().startsWith(name)
						|| cmd.getAliases().stream().anyMatch(s -> s.startsWith(name)))
				.findFirst();
	}

	default Optional<Command> process(LinkedList<String> args) {
		if (args.size() > 1) {
			String first = args.removeFirst();
			Optional<Command> opt = getCommand(first);

			if (opt.isPresent())
				return opt;
		}

		return Optional.empty();
	}

	default void execute(LinkedList<String> args) throws Exception {
		Optional<Command> opt = process(args);

		if (opt.isPresent()) {
			Command cmd = opt.get();

			cmd.execute(args);
			return;
		}

		this.getExecutable().execute(args);
	}

}
