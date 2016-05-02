package instructabilty.command;

import instructabilty.util.Builder;

import java.util.ArrayList;
import java.util.List;

public class CommandBuilder implements Builder<SimpleCommand> {

	private final String name;
	private String desc;
	private List<String> aliases;
	private List<Command> commands;
	private CommandPermission permission;
	private boolean addHelperCommands;

	public CommandBuilder(String name) {
		this.name = name;
		this.desc = "No description";
		this.aliases = new ArrayList<>();
		this.commands = new ArrayList<>();
		this.addHelperCommands = true;
	}

	public CommandBuilder desc(String desc) {
		this.desc = desc;
		return this;
	}

	public CommandBuilder alias(String alias) {
		aliases.add(alias);
		return this;
	}

	public CommandBuilder command(Command command) {
		this.commands.add(command);
		return this;
	}

	public CommandBuilder permission(String permission) {
		if (permission.contains("."))
			this.permission = new CommandPermission(permission);
		else
			this.permission = new CommandPermission(permission, new ArrayList<>());
		return this;
	}

	public CommandBuilder noHelperCommands() {
		this.addHelperCommands = false;
		return this;
	}

	@Override
	public SimpleCommand build() {
		return build((event, msg, args) -> {});
	}

	public SimpleCommand build(CommandExecutable executable) {
		return new SimpleCommand(name,
				desc,
				aliases,
				commands,
				permission,
				executable,
				addHelperCommands);
	}

}
