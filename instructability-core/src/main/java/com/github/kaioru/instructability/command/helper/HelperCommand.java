package com.github.kaioru.instructability.command.helper;

import com.github.kaioru.instructability.command.Command;
import com.github.kaioru.instructability.command.CommandImpl;

public abstract class HelperCommand extends CommandImpl {

	private final Command parent;

	public HelperCommand(Command parent) {
		this.parent = parent;
	}

	public Command getParent() {
		return parent;
	}

	@Override
	public boolean addHelperCommands() { return false; }

}
