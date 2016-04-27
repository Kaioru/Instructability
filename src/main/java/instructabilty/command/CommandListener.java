package instructabilty.command;

import instructabilty.Instructables;
import sx.blah.discord.api.IListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

public class CommandListener implements IListener<MessageReceivedEvent> {

	@Override
	public void handle(MessageReceivedEvent event) {
		String raw = event.getMessage().getContent();
		String pre = ".";

		if (raw.startsWith(pre)) {
			try {
				// TODO: Callback (?)
				Instructables.getRegistry().execute(
						event,
						new MessageBuilder(event.getClient())
								.withChannel(event.getMessage().getChannel())
								.withContent(event.getMessage().getAuthor()
										.mention() + ",\r\n\r\n"),
						raw.substring(pre.length(), raw.length()));
			} catch (Exception e) {

			}
		}
	}

}
