package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.command.CommandImpl;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;

public abstract class Discord4JCommand extends CommandImpl {

	@Override
	public void execute(LinkedList<String> args) throws Exception {}

	public abstract void execute(LinkedList<String> args, MessageReceivedEvent event, MessageBuilder msg) throws Exception;

}