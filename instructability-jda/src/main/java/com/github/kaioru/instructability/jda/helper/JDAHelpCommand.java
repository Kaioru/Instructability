package com.github.kaioru.instructability.jda.helper;

import com.github.kaioru.instructability.command.Command;
import com.github.kaioru.instructability.command.helper.HelpCommand;
import com.github.kaioru.instructability.jda.JDACommandExecutor;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.LinkedList;

public class JDAHelpCommand extends HelpCommand implements JDACommandExecutor {

	public JDAHelpCommand(Command parent) {
		super(parent);
	}

	@Override
	public void execute(LinkedList<String> args, MessageReceivedEvent event) throws Exception {
		event.getChannel().sendMessage(getCommandList());
	}

}
