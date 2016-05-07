package com.github.kaioru.instructability.command;

import com.github.kaioru.instructability.Instructables;
import com.github.kaioru.instructability.command.helper.HelperCommandType;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.modules.IModule;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface Command extends CommandExecutable {

	String getName();

	String getDesc();

	List<String> getAliases();

	List<Command> getCommands();

	default Optional<Command> getCommand(String name) {
		return getCommands().stream()
				.filter(cmd -> cmd.getName().startsWith(name)
						|| cmd.getAliases().stream().anyMatch(s -> s.startsWith(name)))
				.findFirst();
	}

	default Optional<IModule> getModule() {
		return Optional.empty();
	}

	default Optional<CommandPermission> getPermission() {
		return Optional.empty();
	}

	CommandExecutable getExecutable();

	default boolean removeTriggerMessage() {
		return true;
	}

	default boolean allowPrivateMessage() { return false; }

	default void addHelperCommands() {
		addHelperCommands(HelperCommandType.ALL);
	}

	default void addHelperCommands(HelperCommandType... types) {
		for (HelperCommandType type : types) {
			if ((type.getValue() & HelperCommandType.INFO.getValue()) > 0)
				getCommands().add(Commands.getInfoCommand(this));
			if ((type.getValue() & HelperCommandType.HELP.getValue()) > 0)
				getCommands().add(Commands.getHelpCommand(this));
			if ((type.getValue() & HelperCommandType.ALIAS.getValue()) > 0)
				getCommands().add(Commands.getAliasCommand(this));
		}
	}

	@Override
	default void execute(
			MessageReceivedEvent event,
			MessageBuilder msg,
			LinkedList<String> args) throws Exception {
		try {
			if (args.size() > 1) {
				String first = args.removeFirst();
				Optional<Command> opt = getCommand(args.getFirst());

				if (opt.isPresent()) {
					opt.get().execute(event, msg, args);
					return;
				}
			}

			throw new NoSuchElementException();
		} catch (NoSuchElementException e) {
			if (event.getMessage().getChannel().isPrivate()) {
				if (!allowPrivateMessage())
					return;
			}

			if (removeTriggerMessage())
				event.getMessage().delete();

			if (getPermission().isPresent()) {
				CommandPermission reqPermission = getPermission().get();

				if (!Instructables.getPermissionRegistry()
						.getForGuild(event.getMessage().getGuild().getID())
						.checkPermissions(event.getMessage().getAuthor(), event.getMessage().getGuild(), reqPermission)) {
					msg.appendContent(String.format("You do not have the permission '%s' to use the command.", reqPermission.getName()));
					msg.build();
					return;
				}
			}

			getExecutable().execute(event, msg, args);
		}
	}

}
