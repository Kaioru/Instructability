package instructabilty;

import instructabilty.command.CommandRegistry;

public class Instructables {

	private final static CommandRegistry reg = new CommandRegistry();

	public static CommandRegistry getRegistry() {
		return reg;
	}

}
