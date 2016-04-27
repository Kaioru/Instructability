package instructabilty.command;

import java.util.ArrayList;
import java.util.List;

import sx.blah.discord.modules.IModule;

public class CommandBuilder {
	private IModule module;

	private final String name;
	private String desc;
	private List<String> aliases;

	private List<Command> subcommands;
	private boolean hookDefaults;

	public CommandBuilder(String name) {
		this.module = null;

		this.name = name;
		this.desc = "No description";
		this.aliases = new ArrayList<>();

		this.subcommands = new ArrayList<>();
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
		this.subcommands.add(command);
		return this;
	}

	public CommandBuilder skipHookingDefaults() {
		this.hookDefaults = false;
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
				subcommands,
				hookDefaults,
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
