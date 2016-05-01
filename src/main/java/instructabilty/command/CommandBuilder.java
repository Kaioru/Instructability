package instructabilty.command;

import sx.blah.discord.modules.IModule;

import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {
	private final String name;
	private IModule module;
	private String desc;
	private List<String> aliases;

	private List<Command> subCommands;
	private boolean hookDefaults;

	private CommandPermission permission;

	public CommandBuilder(String name) {
		this.module = null;

		this.name = name;
		this.desc = "No description";
		this.aliases = new ArrayList<>();

		this.subCommands = new ArrayList<>();
		this.hookDefaults = true;
	}
	
	public CommandBuilder module(IModule module) {
		this.module = module;
		return this;
	}

	public CommandBuilder desc(String desc) {
		this.desc = desc;
		return this;
	}

	public CommandBuilder alias(String alias) {
		this.aliases.add(alias);
		return this;
	}

	public CommandBuilder command(Command command) {
		this.subCommands.add(command);
		return this;
	}

	public CommandBuilder skipHookingDefaults() {
		this.hookDefaults = false;
		return this;
	}

	public CommandBuilder permission(String permission) {
		this.permission = new CommandPermission(permission);
		return this;
	}

	public CommandOptions buildOptions() {
		return buildOptions((event, msg, args) -> {
		});
	}

	public CommandOptions buildOptions(final CommandExecutable exe) {
		return new CommandOptions(
				module,
				name,
				desc,
				aliases,
				subCommands,
				hookDefaults,
				permission,
				exe);
	}

	public Command build() {
		return build((event, msg, args) -> {
		});
	}

	public Command build(final CommandExecutable exe) {
		return new Command(buildOptions(exe));
	}

}
