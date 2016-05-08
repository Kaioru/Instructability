package com.github.kaioru.instructability;

import com.github.kaioru.instructability.command.CommandRegistry;
import com.github.kaioru.instructability.permission.PermissionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Instructables {

	public static Logger LOGGER = LoggerFactory.getLogger(Instructables.class);

	private static CommandRegistry commandRegistry = new CommandRegistry();
	private static PermissionRegistry permissionRegistry = new PermissionRegistry();

	@Deprecated
	public static CommandRegistry getRegistry() {
		return commandRegistry;
	}

	public static CommandRegistry getCommandRegistry() {
		return commandRegistry;
	}

	public static PermissionRegistry getPermissionRegistry() {
		return permissionRegistry;
	}

}
