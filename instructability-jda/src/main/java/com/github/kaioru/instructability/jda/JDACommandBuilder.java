package com.github.kaioru.instructability.jda;

import com.github.kaioru.instructability.Defaults;
import com.github.kaioru.instructability.command.Command;
import com.github.kaioru.instructability.command.CommandVerifier;
import net.dv8tion.jda.Permission;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JDACommandBuilder {

	private final String name;
	private final List<String> aliases;
	private final List<Command> commands;
	private final List<CommandVerifier> preVerifiers, postVerifiers;
	private String desc;
	private String permission;
	private Permission discordPermission;
	private boolean allowPrivateMessage;
	private boolean removeTriggerMessage;

	public JDACommandBuilder(String name) {
		this.name = name;
		this.desc = Defaults.DESCRIPTION;
		this.permission = Defaults.PERMISSION;

		this.aliases = new ArrayList<>();
		this.commands = new ArrayList<>();
		this.preVerifiers = new ArrayList<>();
		this.postVerifiers = new ArrayList<>();

		this.discordPermission = Permission.MESSAGE_WRITE;

		this.allowPrivateMessage = true;
		this.removeTriggerMessage = true;
	}

	public JDACommandBuilder desc(String desc) {
		this.desc = desc;
		return this;
	}

	public JDACommandBuilder permission(String permission) {
		this.permission = permission;
		return this;
	}

	public JDACommandBuilder alias(String alias) {
		this.aliases.add(alias);
		return this;
	}

	public JDACommandBuilder command(JDACommand command) {
		this.commands.add(command);
		return this;
	}

	public JDACommandBuilder pre(JDACommandVerifier commandVerifier) {
		this.preVerifiers.add(commandVerifier);
		return this;
	}

	public JDACommandBuilder post(JDACommandVerifier commandVerifier) {
		this.postVerifiers.add(commandVerifier);
		return this;
	}

	public JDACommandBuilder permission(Permission permission) {
		this.discordPermission = permission;
		return this;
	}

	public JDACommandBuilder disallowPrivateMessage() {
		this.allowPrivateMessage = false;
		return this;
	}

	public JDACommandBuilder noRemoveTriggerMessage() {
		this.removeTriggerMessage = false;
		return this;
	}

	public JDACommand build() {
		return build((args, event) -> {});
	}

	public JDACommand build(JDACommandExecutor exe) {
		return new JDACommand() {

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
			public Permission getDiscordPermission() {
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
			public void execute(LinkedList<String> args, MessageReceivedEvent event) throws Exception {
				exe.execute(args, event);
			}

		};
	}

}
