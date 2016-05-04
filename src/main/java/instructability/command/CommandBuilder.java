package instructability.command;

import instructability.util.Builder;

import java.util.ArrayList;
import java.util.List;

public class CommandBuilder implements Builder<BuiltCommand> {

	private final String name;
	private String desc;
	private List<String> aliases;
	private List<Command> commands;
	private CommandPermission permission;
	private boolean addHelperCommands;
	private boolean removeTriggerMessage;
	private boolean allowPrivateMessage;

	public CommandBuilder(String name) {
		this.name = name;
		this.desc = "No description";
		this.aliases = new ArrayList<>();
		this.commands = new ArrayList<>();
		this.addHelperCommands = true;
		this.removeTriggerMessage = true;
		this.allowPrivateMessage = false;
	}

	/**
	 * Adds a description to the command
	 *
	 * @param desc
	 * @return
	 */
	public CommandBuilder desc(String desc) {
		this.desc = desc;
		return this;
	}

	/**
	 * Adds an alias to the command
	 *
	 * @param alias
	 * @return
	 */
	public CommandBuilder alias(String alias) {
		aliases.add(alias);
		return this;
	}

	/**
	 * Adds a sub-command to the command
	 *
	 * @param command
	 * @return
	 */
	public CommandBuilder command(Command command) {
		this.commands.add(command);
		return this;
	}

	/**
	 * Adds permission to the command
	 *
	 * @param permission
	 * @return
	 */
	public CommandBuilder permission(String permission) {
		if (permission.contains("."))
			this.permission = new CommandPermission(permission);
		else
			this.permission = new CommandPermission(permission, new ArrayList<>());
		return this;
	}

	/**
	 * Removes helper commands from the command
	 *
	 * @return CommandBuilder
	 */
	public CommandBuilder noHelperCommands() {
		this.addHelperCommands = false;
		return this;
	}

	/**
	 * Removes the ability to remove the message used to trigger the command
	 *
	 * @return CommandBuilder
	 */
	public CommandBuilder noRemoveTrigger() {
		this.removeTriggerMessage = false;
		return this;
	}

	/**
	 * Removes the ability to execute the command from a private channel
	 *
	 * @return CommandBuilder
	 */
	public CommandBuilder noPrivateMessage() {
		this.allowPrivateMessage = false;
		return this;
	}

	/**
	 * Builds the command with the specified options
	 *
	 * @return BuiltCommand
	 */
	@Override
	public BuiltCommand build() {
		return build((event, msg, args) -> {});
	}

	/**
	 * Builds the command with the specified options
	 *
	 * @param executable The command executable for the command
	 * @return BuiltCommand
	 */
	public BuiltCommand build(CommandExecutable executable) {
		return new BuiltCommand(name,
				desc,
				aliases,
				commands,
				permission,
				executable,
				addHelperCommands,
				removeTriggerMessage,
				allowPrivateMessage);
	}

}
