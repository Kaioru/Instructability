package com.github.kaioru.instructability;

import com.github.kaioru.instructability.command.CommandImpl;
import com.github.kaioru.instructability.command.CommandRegistry;
import com.github.kaioru.instructability.command.CommandVerifier;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.LinkedList;

public class InstructablesTest extends TestCase {

	@Test
	public void test() throws Exception {
		CommandRegistry reg = Instructables.getRegistry();
		reg.registerCommand(new TestCommand());
		reg.execute("test 'first argument'");
		reg.execute("non-existent");

		//MultiParamCommandRegistry reg2 = new MultiParamCommandRegistry();
		reg.registerCommand(new MultiParamCommand());
		reg.execute("multi 'from the outside'", "hello");
	}

	@FunctionalInterface
	public interface MultiParamCommandVerifier extends CommandVerifier {

		boolean verify(LinkedList<String> args, String random);

		default boolean verify(LinkedList<String> args) { return false; }

	}

	public class TestCommand extends CommandImpl {

		@Override
		public String getName() {
			return "test";
		}

		@Override
		public String getDesc() {
			return "Test command";
		}

		@Override
		public void execute(LinkedList<String> args) throws Exception {
			assertEquals(args.removeFirst(), "first argument");
		}

	}

	public class MultiParamCommand extends CommandImpl {

		public MultiParamCommand() {
			registerPreVerifier((MultiParamCommandVerifier) (args, random) -> {
				assertEquals(random, "hello");
				return args.size() > 0;
			});
		}

		@Override
		public String getName() {
			return "multi";
		}

		@Override
		public String getDesc() {
			return "Multi param test command";
		}

		@Override
		public void execute(LinkedList<String> args) throws Exception {}

		public void execute(LinkedList<String> args, String random) throws Exception {
			assertEquals(random, "hello");
			assertEquals(args.removeFirst(), "from the outside");
		}

	}

}