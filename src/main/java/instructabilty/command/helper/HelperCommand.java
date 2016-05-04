package instructabilty.command.helper;

import instructabilty.command.Command;
import instructabilty.command.SimpleCommand;

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
