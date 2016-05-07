package com.github.kaioru.instructability;

import com.github.kaioru.instructability.command.CommandRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Instructables {

	public static Logger LOGGER = LoggerFactory.getLogger(Instructables.class);

	private static CommandRegistry registry = new CommandRegistry();

	public static CommandRegistry getRegistry() {
		return registry;
	}

}
