package instructabilty.command;

import java.util.List;

public class CommandOptions {

	private final String name;
	private final String desc;
	private final List<String> aliases;

	private final List<Command> subcommands;
	private final boolean hookDefaults;

	private final CommandExecutable executable;

	public CommandOptions(
			String name,
			String desc,
			List<String> aliases,
			List<Command> subcommands,
			boolean hookDefaults,
			CommandExecutable executable) {
		this.name = name;
		this.desc = desc;
		this.aliases = aliases;

		this.subcommands = subcommands;
		this.hookDefaults = hookDefaults;

		this.executable = executable;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public List<Command> getSubcommands() {
		return subcommands;
	}

	public boolean isHookDefaults() {
		return hookDefaults;
	}

	public CommandExecutable getExecutable() {
		return executable;
	}

}
