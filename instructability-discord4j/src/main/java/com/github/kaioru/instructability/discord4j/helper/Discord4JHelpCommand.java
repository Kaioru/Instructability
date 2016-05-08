package com.github.kaioru.instructability.discord4j.helper;

import com.github.kaioru.instructability.command.Command;
import com.github.kaioru.instructability.command.helper.HelpCommand;
import com.github.kaioru.instructability.discord4j.Discord4JCommandExecutor;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;

public class Discord4JHelpCommand extends HelpCommand implements Discord4JCommandExecutor {

	public Discord4JHelpCommand(Command parent) {
		super(parent);
	}

	@Override
	public void execute(LinkedList<String> args, MessageReceivedEvent event, MessageBuilder msg) throws Exception {
		msg.appendContent(getCommandList());
		msg.build();
	}
}
