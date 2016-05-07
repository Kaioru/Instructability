package com.github.kaioru.instructability.command.annotated;

import com.github.kaioru.instructability.command.Command;

import java.util.LinkedList;
import java.util.Optional;

public interface AnnotatedCommand extends Command, AnnotatedCommandExecutable {

	@Override
	default void execute(LinkedList<String> args, Object... params) throws Exception {
		Optional<Command> opt = process(args);

		if (opt.isPresent()) {
			Command cmd = opt.get();

			if (cmd instanceof AnnotatedCommand)
				((AnnotatedCommand) cmd).execute(args, params);
			return;
		}

		this.getExecutable().execute(args, params);
	}

	@Override
	default void execute(LinkedList<String> args) throws Exception {
		execute(args, null);
	}

	AnnotatedCommandExecutable getExecutable();

}
