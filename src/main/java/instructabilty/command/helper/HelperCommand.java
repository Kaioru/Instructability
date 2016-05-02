package instructabilty.command.helper;

import instructabilty.command.Command;

public abstract class HelperCommand implements Command {

	private final Command parent;

	public HelperCommand(Command parent) {
		this.parent = parent;
	}

	public Command getParent() {
		return parent;
	}

}
