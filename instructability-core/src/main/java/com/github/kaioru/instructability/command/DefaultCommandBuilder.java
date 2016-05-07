package com.github.kaioru.instructability.command;

import java.util.LinkedList;

public class DefaultCommandBuilder extends CommandBuilder<DefaultBuiltCommand, CommandExecutable> {

	public DefaultCommandBuilder(String name) {
		super(name);
	}

	@Override
	public DefaultBuiltCommand build(CommandExecutable commandExecutable) {
		return new DefaultBuiltCommand() {

			@Override
			public String getName() {
				return name;
			}

			@Override
			public String getDesc() {
				return desc;
			}

			@Override
			public void onCommand(LinkedList<String> args) throws Exception {
				commandExecutable.execute(args);
			}

		};
	}

}
