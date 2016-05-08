package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.Defaults;
import com.github.kaioru.instructability.command.Command;
import com.github.kaioru.instructability.command.CommandVerifier;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Discord4JCommandBuilder {

	private final String name;
	private final List<String> aliases;
	private final List<Command> commands;
	private final List<CommandVerifier> preVerifiers, postVerifiers;
	private String desc;
	private String permission;
	private boolean allowPrivateMessage;
	private boolean removeTriggerMessage;

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

	public Discord4JCommandBuilder desc(String desc) {
		this.desc = desc;
		return this;
	}

	public Discord4JCommandBuilder permission(String permission) {
		this.permission = permission;
		return this;
	}

	public Discord4JCommandBuilder alias(String alias) {
		this.aliases.add(alias);
		return this;
	}

	public Discord4JCommandBuilder command(Discord4JCommand command) {
		this.commands.add(command);
		return this;
	}

	public Discord4JCommandBuilder pre(Discord4JCommandVerifier commandVerifier) {
		this.preVerifiers.add(commandVerifier);
		return this;
	}

	public Discord4JCommandBuilder post(Discord4JCommandVerifier commandVerifier) {
		this.postVerifiers.add(commandVerifier);
		return this;
	}

	public Discord4JCommandBuilder disallowPrivateMessage() {
		this.allowPrivateMessage = false;
		return this;
	}

	public Discord4JCommandBuilder noRemoveTriggerMessage() {
		this.removeTriggerMessage = false;
		return this;
	}

	public Discord4JCommand build() {
		return build((args, event, msg) -> {});
	}

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
