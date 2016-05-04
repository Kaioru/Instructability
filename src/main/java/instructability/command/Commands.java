package instructability.command;

import instructability.command.helper.AliasCommand;
import instructability.command.helper.HelpCommand;
import instructability.command.helper.HelperCommand;

public class Commands {

	public static HelperCommand getHelpCommand(Command p) {
		return new HelpCommand(p);
	}

	public static HelperCommand getAliasCommand(Command p) {
		return new AliasCommand(p);
	}

}
