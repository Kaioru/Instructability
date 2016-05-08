package com.github.kaioru.instructability.javacord;

import com.github.kaioru.instructability.Defaults;
import com.github.kaioru.instructability.command.Command;
import com.github.kaioru.instructability.command.CommandVerifier;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.message.Message;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JavacordCommandBuilder {

	private final String name;
	private final List<String> aliases;
	private final List<Command> commands;
	private final List<CommandVerifier> preVerifiers, postVerifiers;
	private String desc;
	private String permission;
	private boolean allowPrivateMessage;
	private boolean removeTriggerMessage;

	public JavacordCommandBuilder(String name) {
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

	public JavacordCommandBuilder desc(String desc) {
		this.desc = desc;
		return this;
	}

	public JavacordCommandBuilder permission(String permission) {
		this.permission = permission;
		return this;
	}

	public JavacordCommandBuilder alias(String alias) {
		this.aliases.add(alias);
		return this;
	}

	public JavacordCommandBuilder command(JavacordCommand command) {
		this.commands.add(command);
		return this;
	}

	public JavacordCommandBuilder pre(JavacordCommandVerifier commandVerifier) {
		this.preVerifiers.add(commandVerifier);
		return this;
	}

	public JavacordCommandBuilder post(JavacordCommandVerifier commandVerifier) {
		this.postVerifiers.add(commandVerifier);
		return this;
	}

	public JavacordCommandBuilder disallowPrivateMessage() {
		this.allowPrivateMessage = false;
		return this;
	}

	public JavacordCommandBuilder noRemoveTriggerMessage() {
		this.removeTriggerMessage = false;
		return this;
	}

	public JavacordCommand build() {
		return build((args, event, msg) -> {});
	}

	public JavacordCommand build(JavacordCommandExecutor exe) {
		return new JavacordCommand() {

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
			public boolean allowPrivateMessage() {
				return allowPrivateMessage;
			}

			@Override
			public boolean removeTriggerMessage() {
				return removeTriggerMessage;
			}

			@Override
			public void execute(LinkedList<String> args, DiscordAPI discordAPI, Message message) throws Exception {
				exe.execute(args, discordAPI, message);
			}

		};
	}

}
