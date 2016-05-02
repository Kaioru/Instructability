package instructabilty.command;

import java.util.List;
import java.util.Optional;

public class SimpleCommand implements Command {

	private final String name;
	private final String desc;
	private final List<String> aliases;
	private final List<Command> commands;
	private final CommandPermission permission;
	private final CommandExecutable executable;

	public SimpleCommand(String name,
	                     String desc,
	                     List<String> aliases,
	                     List<Command> commands,
	                     CommandPermission permission,
	                     CommandExecutable executable,
	                     boolean addHelperCommands) {
		this.name = name;
		this.desc = desc;
		this.aliases = aliases;
		this.commands = commands;
		this.permission = permission;
		this.executable = executable;

		if (addHelperCommands)
			addHelperCommands();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public List<Command> getCommands() {
		return commands;
	}

	@Override
	public Optional<CommandPermission> getPermission() {
		return Optional.ofNullable(permission);
	}

	@Override
	public CommandExecutable getExecutable() {
		return executable;
	}

}
