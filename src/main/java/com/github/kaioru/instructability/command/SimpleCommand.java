package com.github.kaioru.instructability.command;

import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;

public abstract class SimpleCommand extends CommandImpl {

	@Override
	public CommandExecutable getExecutable() {
		return ((event, msg, args) -> {
			onExecute(event, msg, args);
		});
	}

	public abstract void onExecute(MessageReceivedEvent event, MessageBuilder msg, LinkedList<String> args) throws Exception;

}
