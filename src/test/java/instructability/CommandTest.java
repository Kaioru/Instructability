package instructability;

import instructability.command.*;
import instructability.command.helper.HelperCommandType;
import org.junit.Test;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;
import java.util.Optional;

import static org.junit.Assert.*;

public class CommandTest {

	@Test
	public void commandRegisteringTest() {
		CommandRegistry reg = Instructables.getRegistry();

		assertTrue(reg.getCommand("help").isPresent());
		assertTrue(reg.getCommand("alias").isPresent());

		AnnotatedCommands aCmds = new AnnotatedCommands();

		reg.registerCommands(aCmds);

		Optional<Command> annotatedCommand = reg.getCommand("annotation");
		assertTrue(annotatedCommand.isPresent());
		annotatedCommand.ifPresent(cmd -> {
			assertEquals(cmd.getName(), "annotation");
			assertEquals(cmd.getDesc(), "annotation command");
		});

		reg.unregisterCommands(aCmds);

		reg.registerCommand(new CommandBuilder("builder")
				.command(new CommandBuilder("inside").build())
				.permission("builder.required")
				.build());

		Optional<Command> builderCommand = reg.getCommand("builder");
		assertTrue(builderCommand.isPresent());
		builderCommand.ifPresent(cmd -> {
			assertEquals(cmd.getName(), "builder");
			assertTrue(cmd.getCommand("inside").isPresent());
			assertTrue(cmd.getPermission().isPresent());
		});

		reg.registerCommand(new ClassCommand());

		Optional<Command> classCommand = reg.getCommand("class");
		assertTrue(classCommand.isPresent());
		classCommand.ifPresent(cmd -> {
			assertEquals(cmd.getName(), "class");
			assertEquals(cmd.getDesc(), "class command");
			assertFalse(cmd.getCommand("help").isPresent());
		});

		reg.registerCommand(new LimitedHelperCommand());

		Optional<Command> limitedCommand = reg.getCommand("limited");
		assertTrue(limitedCommand.isPresent());
		limitedCommand.ifPresent(cmd -> {
			assertFalse(cmd.getCommand("info").isPresent());
			assertFalse(cmd.getCommand("help").isPresent());
			assertTrue(cmd.getCommand("alias").isPresent());

			cmd.getCommands().clear();
			cmd.addHelperCommands(HelperCommandType.ALL);
			assertTrue(cmd.getCommand("info").isPresent());
			assertTrue(cmd.getCommand("help").isPresent());
			assertTrue(cmd.getCommand("alias").isPresent());
		});
	}

	public class AnnotatedCommands {

		@AnnotatedCommand(name = "annotation", desc = "annotation command")
		public void onTestCommand(MessageReceivedEvent event, MessageBuilder msg, LinkedList<String> args) throws Exception {

		}

	}

	public class ClassCommand extends SimpleCommand {

		@Override
		public String getName() {
			return "class";
		}

		@Override
		public String getDesc() {
			return "class command";
		}

		@Override
		public void onExecute(MessageReceivedEvent event, MessageBuilder msg, LinkedList<String> args) throws Exception {

		}

	}

	public class LimitedHelperCommand extends SimpleCommand {

		public LimitedHelperCommand() {
			addHelperCommands(HelperCommandType.ALIAS);
		}

		@Override
		public String getName() {
			return "limited";
		}

		@Override
		public String getDesc() {
			return "limited command";
		}

		@Override
		public void onExecute(MessageReceivedEvent event, MessageBuilder msg, LinkedList<String> args) throws Exception {

		}

	}

}
