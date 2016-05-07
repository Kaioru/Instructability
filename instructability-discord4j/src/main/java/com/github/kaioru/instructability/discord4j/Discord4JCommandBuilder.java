package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.command.CommandBuilder;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;

public class Discord4JCommandBuilder extends CommandBuilder<Discord4JCommand, Discord4JCommandExecutable> {

	public Discord4JCommandBuilder(String name) {
		super(name);
	}

	@Override
	public Discord4JCommandBuilder desc(String desc) {
		super.desc(desc);
		return this;
	}

	@Override
	public Discord4JCommand build(Discord4JCommandExecutable discord4JCommandExecutable) {
		return new Discord4JCommandImpl() {

			@Override
			public String getName() {
				return name;
			}

			@Override
			public String getDesc() {
				return desc;
			}

			@Override
			public void onCommand(LinkedList<String> args, MessageReceivedEvent event, MessageBuilder msg) throws Exception {
				discord4JCommandExecutable.execute(args, event, msg);
			}

		};
	}
}
