package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.command.CommandRegistry;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

public class Discord4JCommandRegistry extends CommandRegistry implements Discord4JCommand {

	@Override
	public Discord4JCommandExecutable getExecutable() {
		return this;
	}

	public void execute(String input, MessageReceivedEvent event, MessageBuilder msg) throws Exception {
		execute(getArgsFromMessage(input), event, msg);
	}
}
