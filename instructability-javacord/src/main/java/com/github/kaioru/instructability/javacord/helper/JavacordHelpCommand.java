package com.github.kaioru.instructability.javacord.helper;

import com.github.kaioru.instructability.command.Command;
import com.github.kaioru.instructability.command.helper.HelpCommand;
import com.github.kaioru.instructability.javacord.JavacordCommandExecutor;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.message.Message;

import java.util.LinkedList;

public class JavacordHelpCommand extends HelpCommand implements JavacordCommandExecutor {

	public JavacordHelpCommand(Command parent) {
		super(parent);
	}

	@Override
	public void execute(LinkedList<String> args, DiscordAPI discordAPI, Message message) throws Exception {
		message.reply(getCommandList());
	}

}
