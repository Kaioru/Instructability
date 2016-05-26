package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.command.CommandVerifier;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;

/**
 * Used to represent a verifier for a Discord4J command.
 */
@FunctionalInterface
public interface Discord4JCommandVerifier extends CommandVerifier {

	default boolean verify(LinkedList<String> args) { return false; }

	/**
	 * /**
	 * Called when a command is verified.
	 *
	 * @param args  Remaining command arguments.
	 * @param event MessageReceivedEvent used to trigger the command.
	 * @param msg   MessageBuilder for message replies.
	 * @throws Exception
	 */
	boolean verify(LinkedList<String> args, MessageReceivedEvent event, MessageBuilder msg) throws Exception;

}
