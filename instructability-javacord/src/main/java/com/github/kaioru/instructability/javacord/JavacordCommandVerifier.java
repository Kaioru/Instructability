package com.github.kaioru.instructability.javacord;

import com.github.kaioru.instructability.command.CommandVerifier;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.message.Message;

import java.util.LinkedList;

@FunctionalInterface
public interface JavacordCommandVerifier extends CommandVerifier {

	default boolean verify(LinkedList<String> args) { return false; }

	boolean verify(LinkedList<String> args, DiscordAPI discordAPI, Message message) throws Exception;

}
