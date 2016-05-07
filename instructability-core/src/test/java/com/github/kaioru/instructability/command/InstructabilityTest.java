package com.github.kaioru.instructability.command;

import com.github.kaioru.instructability.command.annotated.BasicCommand;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InstructabilityTest {

	@Test
	public void test() throws Exception {
		CommandRegistry reg = new CommandRegistry();

		reg.registerCommand(new CommandTest());
		assertTrue(reg.getCommand("test").isPresent());
		reg.execute("test arg1");

		reg.registerCommand(new DefaultCommandBuilder("builder")
				.build(args -> assertEquals(args.removeFirst(), "arg1")));
		reg.execute("builder arg1");

		reg.registerCommands(new AnnotationsTest());
		assertTrue(reg.getCommand("annotated").isPresent());
		reg.execute("annotated arg1 'arg 2'");
	}

	class AnnotationsTest {

		@BasicCommand(
				name = "annotated",
				desc = "annotated test"
		)
		public void onAnnotatedCommand(LinkedList<String> args) throws Exception {
			assertEquals(args.removeFirst(), "arg1");
			assertEquals(args.removeFirst(), "arg 2");
		}

	}

	class CommandTest extends CommandImpl {

		@Override
		public String getName() {
			return "test";
		}

		@Override
		public String getDesc() {
			return "testing";
		}

		@Override
		public void onCommand(LinkedList<String> args) throws Exception {
			assertEquals(args.removeFirst(), "arg1");
		}

	}

}
