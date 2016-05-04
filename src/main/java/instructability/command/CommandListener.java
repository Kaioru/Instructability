package instructability.command;

import instructability.Instructables;
import sx.blah.discord.Discord4J;
import sx.blah.discord.api.IListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

public class CommandListener implements IListener<MessageReceivedEvent> {

	@Override
	public void handle(MessageReceivedEvent event) {
		CommandRegistry reg = Instructables.getRegistry();
		String raw = event.getMessage().getContent();
		String pre = reg.getCommandPrefix();

		if (raw.startsWith(pre)) {
			try {
				reg.execute(
						event,
						new MessageBuilder(event.getClient())
								.withChannel(event.getMessage().getChannel())
								.withContent(event.getMessage().getAuthor()
										.mention() + ",\r\n\r\n"),
						raw.substring(pre.length(), raw.length()));
			} catch (Exception e) {
				Discord4J.LOGGER.debug("Failed to execute command '" + raw + "'", e);
			}
		}
	}

}
