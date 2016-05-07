package com.github.kaioru.instructability.command.helper;

import com.github.kaioru.instructability.command.Command;
import com.github.kaioru.instructability.command.SimpleCommand;

public abstract class HelperCommand extends SimpleCommand {

	private final Command parent;

	public HelperCommand(Command parent) {
		this.parent = parent;
	}

	public Command getParent() {
		return parent;
	}

	@Override
	public boolean allowPrivateMessage() {
		return true;
	}

}
