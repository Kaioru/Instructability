package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.command.CommandImpl;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;

public abstract class Discord4JCommandImpl extends CommandImpl implements Discord4JCommand {

	@Override
	public Discord4JCommandExecutable getExecutable() {
		return this::onCommand;
	}

	@Override
	public void onCommand(LinkedList<String> args) throws Exception {}

	public abstract void onCommand(LinkedList<String> args, MessageReceivedEvent event, MessageBuilder msg) throws Exception;

}
