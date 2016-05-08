package com.github.kaioru.instructability;

import com.github.kaioru.instructability.command.CommandImpl;
import com.github.kaioru.instructability.command.CommandReference;
import com.github.kaioru.instructability.command.CommandRegistry;
import com.github.kaioru.instructability.command.CommandVerifier;
import com.github.kaioru.instructability.util.PermissionUtil;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.LinkedList;

public class InstructablesTest extends TestCase {

	@Test
	public void test() throws Exception {
		CommandRegistry reg = Instructables.getCommandRegistry();
		TestCommand cmd = new TestCommand();
		cmd.registerCommand(new CommandImpl() {
			@Override
			public String getName() {
				return "inside";
			}

			@Override
			public String getDesc() {
				return null;
			}

			@Override
			public void execute(LinkedList<String> args) throws Exception {
				assertEquals("argument", args.removeFirst());
			}
		});
		reg.registerCommand(cmd);
		reg.execute("test 'first argument'");
		reg.execute("test inside 'argument'");
		reg.execute("non-existent");

		reg.registerCommand(new MultiParamCommand());
		reg.execute("multi 'from the outside'", "hello");

		reg.registerCommands(new InstructablesTest());
		reg.execute("reference inside!");

		assertTrue(PermissionUtil.checkPermission("*", "permission.test"));
		assertTrue(PermissionUtil.checkPermission("permission.*", "permission.test"));
		assertTrue(PermissionUtil.checkPermission("permission.*", "permission.ing"));
		assertTrue(PermissionUtil.checkPermission("permission.test", "permission.test"));
		assertTrue(PermissionUtil.checkPermission("permission.test.ing", "permission.test"));
		assertTrue(PermissionUtil.checkPermission("permission.test", "permission.test.ing"));
		assertTrue(PermissionUtil.checkPermission("permission.test", "permission.test.ed"));
		assertTrue(PermissionUtil.checkPermission("permission.test.*", "permission.test.ed"));
		assertTrue(PermissionUtil.checkPermission("permission", ""));
		assertFalse(PermissionUtil.checkPermission("test", "permission.test"));
	}

	@CommandReference
	public CommandImpl getReferencedCommand() {
		return new CommandImpl() {

			@Override
			public String getName() {
				return "reference";
			}

			@Override
			public String getDesc() {
				return Defaults.DESCRIPTION;
			}

			@Override
			public void execute(LinkedList<String> args) throws Exception {
				assertEquals("inside!", args.removeFirst());
			}

		};
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
			return Defaults.DESCRIPTION;
		}

		@Override
		public void execute(LinkedList<String> args) throws Exception {
			assertEquals("first argument", args.removeFirst());
		}

	}

	public class MultiParamCommand extends CommandImpl {

		public MultiParamCommand() {
			registerPreVerifier((MultiParamCommandVerifier) (args, random) -> {
				assertEquals("hello", random);
				return args.size() > 0;
			});
			registerPostVerifier((MultiParamCommandVerifier) (args, random) -> {
				assertEquals(0, args.size());
				return true;
			});
		}

		@Override
		public String getName() {
			return "multi";
		}

		@Override
		public String getDesc() {
			return Defaults.DESCRIPTION;
		}

		@Override
		public void execute(LinkedList<String> args) throws Exception {}

		public void execute(LinkedList<String> args, String random) throws Exception {
			assertEquals("hello", random);
			assertEquals("from the outside", args.removeFirst());
		}

	}

}