package instructabilty.command;

import sx.blah.discord.modules.IModule;

import java.util.List;
import java.util.Optional;

public class CommandOptions {

	private final IModule module;

	private final String name;
	private final String desc;
	private final List<String> aliases;

	private final List<Command> subCommands;
	private final boolean hookDefaults;

	private final CommandPermission permission;
	private final CommandExecutable executable;

	public CommandOptions(
			IModule module,
			String name,
			String desc,
			List<String> aliases,
			List<Command> subCommands,
			boolean hookDefaults,
			CommandPermission permission,
			CommandExecutable executable) {
		this.module = module;

		this.name = name;
		this.desc = desc;
		this.aliases = aliases;

		this.subCommands = subCommands;
		this.hookDefaults = hookDefaults;

		this.permission = permission;
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

	public List<Command> getSubCommands() {
		return subCommands;
	}

	public boolean isHookDefaults() {
		return hookDefaults;
	}

	public Optional<CommandPermission> getPermission() { return Optional.ofNullable(permission); }

	public CommandExecutable getExecutable() {
		return executable;
	}

	public Optional<IModule> getModule() {
		return Optional.ofNullable(module);
	}

}
