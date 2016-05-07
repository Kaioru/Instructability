package com.github.kaioru.instructability.command;

public abstract class CommandBuilder<T extends Command, E extends CommandExecutable> {

	protected final String name;
	protected String desc;

	public CommandBuilder(String name) {
		this.name = name;
		this.desc = "No description";
	}

	public CommandBuilder desc(String desc) {
		this.desc = desc;
		return this;
	}

	public abstract T build(E e);

}
