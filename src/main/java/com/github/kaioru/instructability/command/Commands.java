package com.github.kaioru.instructability.command;

import com.github.kaioru.instructability.command.helper.AliasCommand;
import com.github.kaioru.instructability.command.helper.HelpCommand;
import com.github.kaioru.instructability.command.helper.HelperCommand;
import com.github.kaioru.instructability.command.helper.InfoCommand;

public class Commands {

	public static InfoCommand getInfoCommand(Command p) {
		return new InfoCommand(p);
	}

	public static HelperCommand getHelpCommand(Command p) {
		return new HelpCommand(p);
	}

	public static HelperCommand getAliasCommand(Command p) {
		return new AliasCommand(p);
	}

}
