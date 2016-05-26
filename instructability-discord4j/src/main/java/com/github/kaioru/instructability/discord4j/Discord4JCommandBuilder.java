package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.Defaults;
import com.github.kaioru.instructability.command.Command;
import com.github.kaioru.instructability.command.CommandVerifier;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.MessageBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class designed to make command creation easier.
 */
public class Discord4JCommandBuilder {

	private final String name;
	private final List<String> aliases;
	private final List<Command> commands;
	private final List<CommandVerifier> preVerifiers, postVerifiers;
	private String desc;
	private String permission;
	private Permissions discordPermission;
	private boolean allowPrivateMessage;
	private boolean removeTriggerMessage;

	/**
	 * @param name Sets the name of the command.
	 */
	public Discord4JCommandBuilder(String name) {
		this.name = name;
		this.desc = Defaults.DESCRIPTION;
		this.permission = Defaults.PERMISSION;

		this.aliases = new ArrayList<>();
		this.commands = new ArrayList<>();
		this.preVerifiers = new ArrayList<>();
		this.postVerifiers = new ArrayList<>();

		this.allowPrivateMessage = true;
		this.removeTriggerMessage = true;
	}

	/**
	 * @param desc Sets the description of the command.
	 * @return Discord4JCommandBuilder instance.
	 */
	public Discord4JCommandBuilder desc(String desc) {
		this.desc = desc;
		return this;
	}

	/**
	 * @param permission Sets the permission of the command.
	 * @return Discord4JCommandBuilder instance.
	 */
	public Discord4JCommandBuilder permission(String permission) {
		this.permission = permission;
		return this;
	}

	/**
	 * @param alias Adds an alias to the command.
	 * @return Discord4JCommandBuilder instance.
	 */
	public Discord4JCommandBuilder alias(String alias) {
		this.aliases.add(alias);
		return this;
	}

	/**
	 * @param command Adds a subcommand to the command.
	 * @return Discord4JCommandBuilder instance.
	 */
	public Discord4JCommandBuilder command(Discord4JCommand command) {
		this.commands.add(command);
		return this;
	}

	/**
	 * @param commandVerifier Adds a pre-execution verifier to the command.
	 * @return Discord4JCommandBuilder instance.
	 */
	public Discord4JCommandBuilder pre(Discord4JCommandVerifier commandVerifier) {
		this.preVerifiers.add(commandVerifier);
		return this;
	}

	/**
	 * @param commandVerifier Adds a post-execution verifier to the command.
	 * @return Discord4JCommandBuilder instance.
	 */
	public Discord4JCommandBuilder post(Discord4JCommandVerifier commandVerifier) {
		this.postVerifiers.add(commandVerifier);
		return this;
	}

	/**
	 * @param permission Sets the Discord permission to the command.
	 * @return Discord4JCommandBuilder instance.
	 */
	public Discord4JCommandBuilder permission(Permissions permission) {
		this.discordPermission = permission;
		return this;
	}

	/**
	 * Disallow execution of command via private messages.
	 *
	 * @return Discord4JCommandBuilder instance.
	 */
	public Discord4JCommandBuilder disallowPrivateMessage() {
		this.allowPrivateMessage = false;
		return this;
	}

	/**
	 * Disallow removal of trigger message after command execution.
	 *
	 * @return Discord4JCommandBuilder instance.
	 */
	public Discord4JCommandBuilder noRemoveTriggerMessage() {
		this.removeTriggerMessage = false;
		return this;
	}

	/**
	 * Builds the command without any executables.
	 *
	 * @return The built command.
	 */
	public Discord4JCommand build() {
		return build((args, event, msg) -> {});
	}

	/**
	 * Builds the command with a executable.
	 *
	 * @param exe The CommandExecutor for the command.
	 * @return The built command.
	 */
	public Discord4JCommand build(Discord4JCommandExecutor exe) {
		return new Discord4JCommand() {

			@Override
			public String getName() {
				return name;
			}

			@Override
			public String getDesc() {
				return desc;
			}

			@Override
			public String getPermission() {
				return permission;
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
			public List<CommandVerifier> getPreVerifiers() {
				return preVerifiers;
			}

			@Override
			public List<CommandVerifier> getPostVerifiers() {
				return postVerifiers;
			}

			@Override
			public Permissions getDiscordPermission() {
				return discordPermission;
			}

			@Override
			public boolean allowPrivateMessage() {
				return allowPrivateMessage;
			}

			@Override
			public boolean removeTriggerMessage() {
				return removeTriggerMessage;
			}

			@Override
			public void execute(LinkedList<String> args, MessageReceivedEvent event, MessageBuilder msg) throws Exception {
				exe.execute(args, event, msg);
			}

		};
	}

}
