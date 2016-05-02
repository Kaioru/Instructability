package instructabilty.command.helper;

import instructabilty.command.Command;
import instructabilty.command.CommandImpl;

public abstract class HelperCommand extends CommandImpl {

	private final Command parent;

	public HelperCommand(Command parent) {
		this.parent = parent;
	}

	public Command getParent() {
		return parent;
	}

}
