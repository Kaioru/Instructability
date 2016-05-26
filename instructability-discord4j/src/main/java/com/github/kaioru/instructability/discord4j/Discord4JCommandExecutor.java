package com.github.kaioru.instructability.discord4j;

import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;

/**
 * Used to represent an executable Discord4J command.
 */
@FunctionalInterface
public interface Discord4JCommandExecutor {

	/**
	 * Called when a command is executed.
	 *
	 * @param args  Remaining command arguments.
	 * @param event MessageReceivedEvent used to trigger the command.
	 * @param msg   MessageBuilder for message replies.
	 * @throws Exception
	 */
	void execute(LinkedList<String> args, MessageReceivedEvent event, MessageBuilder msg) throws Exception;

}
