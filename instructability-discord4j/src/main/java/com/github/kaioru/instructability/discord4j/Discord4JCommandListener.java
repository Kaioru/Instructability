package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.command.Instructables;
import sx.blah.discord.Discord4J;
import sx.blah.discord.api.IListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

public class Discord4JCommandListener implements IListener<MessageReceivedEvent> {

	@Override
	public void handle(MessageReceivedEvent event) {
		Discord4JCommandRegistry reg = (Discord4JCommandRegistry) Instructables.getRegistry();
		String raw = event.getMessage().getContent();
		String pre = ".";

		if (raw.startsWith(pre)) {
			try {
				reg.execute(
						raw.substring(pre.length(), raw.length()),
						event,
						new MessageBuilder(event.getClient())
								.withChannel(event.getMessage().getChannel())
								.withContent(event.getMessage().getAuthor()
										.mention() + ",\r\n\r\n"));
			} catch (Exception e) {
				Discord4J.LOGGER.debug("Failed to execute command '" + raw + "'", e);
			}
		}
	}

}