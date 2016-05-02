package instructabilty.command;

import instructabilty.command.helper.AliasCommand;
import instructabilty.command.helper.HelpCommand;
import instructabilty.command.helper.HelperCommand;

public class Commands {

	public static HelperCommand getHelpCommand(Command p) {
		return new HelpCommand(p);
	}

	public static HelperCommand getAliasCommand(Command p) {
		return new AliasCommand(p);
	}

}
