package com.github.kaioru.instructability.command;

import java.util.LinkedList;

@FunctionalInterface
public interface CommandExecutor {

	void execute(LinkedList<String> args) throws Exception;

}
