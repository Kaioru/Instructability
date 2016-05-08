package com.github.kaioru.instructability.jda;

import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.LinkedList;

@FunctionalInterface
public interface JDACommandExecutor {

	void execute(LinkedList<String> args, MessageReceivedEvent event) throws Exception;

}
