package instructability.command;

import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;

public interface CommandExecutable {

	void execute(MessageReceivedEvent event, MessageBuilder msg, LinkedList<String> args)
			throws Exception;

}
