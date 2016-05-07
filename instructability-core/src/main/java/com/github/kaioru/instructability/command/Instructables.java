package com.github.kaioru.instructability.command;

public class Instructables {

	private static CommandRegistry reg = new CommandRegistry();

	public static CommandRegistry getRegistry() {
		return reg;
	}

	public static void setRegistry(CommandRegistry reg) {
		Instructables.reg = reg;
	}

}
