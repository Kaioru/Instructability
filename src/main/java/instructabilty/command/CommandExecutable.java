package instructabilty.command;

import java.util.LinkedList;

import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

public interface CommandExecutable {

	public void execute(MessageReceivedEvent event, MessageBuilder msg, LinkedList<String> args)
			throws Exception;

}
