package com.github.kaioru.instructability.javacord;

import com.github.kaioru.instructability.Instructables;
import com.github.kaioru.instructability.command.CommandRegistry;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;

public class JavacordCommandListener implements MessageCreateListener {

	@Override
	public void onMessageCreate(DiscordAPI discordAPI, Message message) {
		CommandRegistry reg = Instructables.getCommandRegistry();
		String raw = message.getContent();
		String pre = reg.getPrefix();

		if (raw.startsWith(pre)) {
			try {
				reg.execute(
						raw.substring(pre.length(), raw.length()),
						discordAPI,
						message);
			} catch (Exception e) {
				Instructables.LOGGER.debug("Failed to execute command '" + raw + "'", e);
			}
		}
	}

}
