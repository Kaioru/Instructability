package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.command.CommandExecutable;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;

@FunctionalInterface
public interface Discord4JCommandExecutable extends CommandExecutable {

	void execute(LinkedList<String> args, MessageReceivedEvent event, MessageBuilder msg) throws Exception;

	@Override
	default void execute(LinkedList<String> args) throws Exception {}

}
