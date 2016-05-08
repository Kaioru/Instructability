package com.github.kaioru.instructability.javacord;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.message.Message;

import java.util.LinkedList;

@FunctionalInterface
public interface JavacordCommandExecutor {

	void execute(LinkedList<String> args, DiscordAPI discordAPI, Message message) throws Exception;

}
