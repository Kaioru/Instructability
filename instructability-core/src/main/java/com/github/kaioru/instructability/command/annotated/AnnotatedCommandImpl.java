package com.github.kaioru.instructability.command.annotated;

import com.github.kaioru.instructability.command.CommandImpl;

import java.util.LinkedList;

public abstract class AnnotatedCommandImpl extends CommandImpl implements AnnotatedCommand {

	@Override
	public AnnotatedCommandExecutable getExecutable() {
		return this::onCommand;
	}

	@Override
	public void execute(LinkedList<String> args, Object... params) throws Exception {
		onCommand(args, params);
	}

	@Override
	public void onCommand(LinkedList<String> args) throws Exception {}

	public abstract void onCommand(LinkedList<String> args, Object... params) throws Exception;

}
