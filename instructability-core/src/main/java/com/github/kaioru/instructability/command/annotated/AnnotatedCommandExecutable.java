package com.github.kaioru.instructability.command.annotated;

import com.github.kaioru.instructability.command.CommandExecutable;

import java.util.LinkedList;

@FunctionalInterface
public interface AnnotatedCommandExecutable extends CommandExecutable {

	void execute(LinkedList<String> args, Object... params) throws Exception;

	@Override
	default void execute(LinkedList<String> args) throws Exception {
		execute(args, null);
	}

}
