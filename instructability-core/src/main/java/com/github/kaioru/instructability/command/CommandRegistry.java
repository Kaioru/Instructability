package com.github.kaioru.instructability.command;

import com.github.kaioru.instructability.Defaults;
import com.github.kaioru.instructability.util.CommandUtil;

import java.util.LinkedList;

public class CommandRegistry extends CommandImpl {

	private final String prefix;

	public CommandRegistry() {
		this.prefix = Defaults.REGISTRY_PREFIX;
	}

	@Override
	public String getName() {
		return Defaults.REGISTRY_NAME;
	}

	@Override
	public String getDesc() {
		return Defaults.REGISTRY_DESC;
	}

	public String getPrefix() {
		return prefix;
	}

	@Override
	public void execute(LinkedList<String> args) throws Exception {
		execute(args);
	}

	public void execute(LinkedList<String> args, Object... params) throws Exception {
		CommandUtil.executeCommand(this, args, params);
	}

	public void execute(String text, Object... params) throws Exception {
		execute(CommandUtil.getArgsFromText(text), params);
	}

}
