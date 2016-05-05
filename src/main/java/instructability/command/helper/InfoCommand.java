package instructability.command.helper;

import instructability.command.Command;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;

public class InfoCommand extends HelperCommand {

	public InfoCommand(Command parent) {
		super(parent);
	}

	@Override
	public String getName() {
		return "info";
	}

	@Override
	public String getDesc() {
		return "Displays information on the current command";
	}

	@Override
	public void onExecute(MessageReceivedEvent event, MessageBuilder msg, LinkedList<String> args) throws Exception {
		msg.appendContent(String.format(
				"%s - %s",
				getParent().getName(),
				getParent().getDesc()
		));
		msg.build();
	}

}
