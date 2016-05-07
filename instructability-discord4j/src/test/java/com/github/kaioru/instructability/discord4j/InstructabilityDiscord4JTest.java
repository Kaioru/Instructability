package com.github.kaioru.instructability.discord4j;

import org.junit.Test;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;

import static org.junit.Assert.assertTrue;

public class InstructabilityDiscord4JTest {

	@Test
	public void test() {
		Discord4JCommandRegistry reg = new Discord4JCommandRegistry();

		reg.registerCommand(new Discord4JCommandBuilder("builder")
				.build((event, msg, args) -> {}));
		assertTrue(reg.getCommand("builder").isPresent());

		reg.registerCommand(new TestCommand());
		assertTrue(reg.getCommand("test").isPresent());
	}

	class TestCommand extends Discord4JCommandImpl {

		@Override
		public String getName() {
			return "test";
		}

		@Override
		public String getDesc() {
			return "Discord4J test command";
		}

		@Override
		public void onCommand(LinkedList<String> args, MessageReceivedEvent event, MessageBuilder msg) throws Exception {

		}

	}

}
