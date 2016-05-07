package com.github.kaioru.instructability.command;

import com.github.kaioru.instructability.command.annotated.AnnotatedCommandBuilder;
import com.github.kaioru.instructability.command.annotated.AnnotatedCommandExecutable;
import com.github.kaioru.instructability.command.annotated.BasicCommand;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CommandImpl implements Command {

	private final List<String> aliases;
	private final List<Command> commands;

	public CommandImpl() {
		this.aliases = new ArrayList<>();
		this.commands = new ArrayList<>();
	}

	@Override
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public List<Command> getCommands() {
		return commands;
	}

	@Override
	public CommandExecutable getExecutable() {
		return this::onCommand;
	}

	public abstract void onCommand(LinkedList<String> args) throws Exception;

	public void addAlias(String alias) {
		this.aliases.add(alias);
	}

	public void removeAlias(String alias) {
		this.aliases.remove(alias);
	}

	public void registerCommand(Command cmd) {
		this.commands.add(cmd);
	}

	public void registerCommands(Object object) {
		for (Method method : object.getClass().getMethods()) {
			if (method.isAnnotationPresent(BasicCommand.class)) {
				BasicCommand a = method.getAnnotation(BasicCommand.class);

				if (method.getParameterCount() == a.parameterCount()) {
					registerCommand(new AnnotatedCommandBuilder(a.name())
							.desc(a.desc())
							.build((AnnotatedCommandExecutable) (args, params) -> {
								if (a.parameterCount() == 1)
									method.invoke(object, args);
								else
									method.invoke(object, args, params);
							}));
				}
			}
		}
	}

	public void unregisterCommand(Command cmd) {
		this.commands.remove(cmd);
	}

	public void unregisterCommands(Object object) {
		for (Method method : object.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(BasicCommand.class)) {
				BasicCommand a = method.getAnnotation(BasicCommand.class);

				if (method.getParameterCount() == a.parameterCount()) {
					List<Command> cmds = commands.stream()
							.filter(c -> c.getName().equals(a.name())
									&& c.getDesc().equals(a.desc()))
							.collect(Collectors.toList());

					cmds.forEach(this::unregisterCommand);
				}
			}
		}
	}

	@Override
	public String toString() {
		return getName() + " - " + getDesc();
	}

}
