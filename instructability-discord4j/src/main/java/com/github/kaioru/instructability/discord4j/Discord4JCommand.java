package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.command.Command;
import com.github.kaioru.instructability.command.annotated.AnnotatedCommand;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;
import java.util.Optional;

public interface Discord4JCommand extends Command, Discord4JCommandExecutable {

	@Override
	default void execute(LinkedList<String> args, MessageReceivedEvent event, MessageBuilder msg) throws Exception {
		Optional<Command> opt = process(args);

		if (opt.isPresent()) {
			Command cmd = opt.get();

			if (cmd instanceof Discord4JCommand)
				((Discord4JCommand) cmd).execute(args, event, msg);
			if (cmd instanceof AnnotatedCommand)
				((AnnotatedCommand) cmd).execute(args, event, msg);
			return;
		}

		if (!allowPrivateMessage())
			if (event.getMessage().getChannel().isPrivate())
				return;

		if (getPermission().isPresent()) {
			Discord4JCommandPermission reqPermission = getPermission().get();

			if (!Discord4JInstructability.getInstance().getPermissionRegistry()
					.getForGuild(event.getMessage().getGuild().getID())
					.checkPermissions(event.getMessage().getAuthor(), event.getMessage().getGuild(), reqPermission)) {
				msg.appendContent(String.format("You do not have the permission '%s' to use the command.", reqPermission.getName()));
				msg.build();
				return;
			}
		}

		this.getExecutable().execute(args, event, msg);

		if (removeTriggerMessage())
			event.getMessage().delete();
	}

	@Override
	default void execute(LinkedList<String> args) throws Exception {}

	Discord4JCommandExecutable getExecutable();

	default boolean removeTriggerMessage() {
		return true;
	}

	default boolean allowPrivateMessage() { return false; }

	default Optional<Discord4JCommandPermission> getPermission() {
		return Optional.empty();
	}

}
