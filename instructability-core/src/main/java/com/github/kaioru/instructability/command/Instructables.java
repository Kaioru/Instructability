package com.github.kaioru.instructability.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Instructables {

	public static Logger LOGGER = LoggerFactory.getLogger(Instructables.class);

	private static CommandRegistry reg = new CommandRegistry();

	public static CommandRegistry getRegistry() {
		return reg;
	}

	public static void setRegistry(CommandRegistry reg) {
		Instructables.reg = reg;
	}

}
