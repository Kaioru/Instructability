package com.github.kaioru.instructability.discord4j;

import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;

@FunctionalInterface
public interface Discord4JCommandExecutor {

	void execute(LinkedList<String> args, MessageReceivedEvent event, MessageBuilder msg) throws Exception;

}
