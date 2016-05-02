package instructabilty.command;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandImpl implements Command {

	private final List<String> aliases;
	private final List<Command> commands;

	public CommandImpl() {
		this.aliases = new ArrayList<>();
		this.commands = new ArrayList<>();
	}

	@Override
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public List<Command> getCommands() {
		return commands;
	}

}
