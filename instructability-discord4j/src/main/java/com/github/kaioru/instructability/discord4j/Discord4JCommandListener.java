package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.Instructables;
import com.github.kaioru.instructability.command.CommandRegistry;
import sx.blah.discord.api.IListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

/**
 * A MessageReceivedEvent listener for commands.
 */
public class Discord4JCommandListener implements IListener<MessageReceivedEvent> {

	@Override
	public void handle(MessageReceivedEvent event) {
		CommandRegistry reg = Instructables.getCommandRegistry();
		String raw = event.getMessage().getContent();
		String pre = reg.getPrefix();

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
				Instructables.LOGGER.debug("Failed to execute command '" + raw + "'", e);
			}
		}
	}

}
