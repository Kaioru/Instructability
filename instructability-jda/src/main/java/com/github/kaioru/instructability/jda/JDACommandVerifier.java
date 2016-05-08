package com.github.kaioru.instructability.jda;

import com.github.kaioru.instructability.command.CommandVerifier;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.LinkedList;

@FunctionalInterface
public interface JDACommandVerifier extends CommandVerifier {

	default boolean verify(LinkedList<String> args) { return false; }

	boolean verify(LinkedList<String> args, MessageReceivedEvent event) throws Exception;

}
