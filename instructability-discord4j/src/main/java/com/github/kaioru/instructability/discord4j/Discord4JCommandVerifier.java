package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.command.CommandVerifier;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;

@FunctionalInterface
public interface Discord4JCommandVerifier extends CommandVerifier {

	default boolean verify(LinkedList<String> args) { return false; }

	boolean verify(LinkedList<String> args, MessageReceivedEvent event, MessageBuilder msg) throws Exception;

}
