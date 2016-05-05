package instructability.command;

import sx.blah.discord.Discord4J;

import java.lang.reflect.Method;
import java.util.ArrayList;
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

	public void registerCommand(Command cmd) {
		this.commands.add(cmd);
		Discord4J.LOGGER.debug(String.format(
				"Registered command '%s' to '%s'",
				cmd.getName(),
				this.getName()
		));
	}

	public void registerCommands(Object object) {
		for (Method method : object.getClass().getMethods())
			if (method.getParameterCount() == 3 && method.isAnnotationPresent(AnnotatedCommand.class)) {
				AnnotatedCommand a = method.getAnnotation(AnnotatedCommand.class);

				CommandBuilder builder = new CommandBuilder(a.name())
						.desc(a.desc());

				if (!a.removeTriggerMessage()) builder.noRemoveTrigger();
				if (!a.allowPrivateMessage()) builder.noPrivateMessage();
				if (!a.addHelperCommands()) builder.noHelperCommands();

				registerCommand(builder.build((event, msg, args) -> method.invoke(object, event, msg, args)));
			}
	}

	public void unregisterCommand(Command cmd) {
		this.commands.remove(cmd);
	}

	public void unregisterCommands(Object object) {
		for (Method method : object.getClass().getDeclaredMethods()) {
			if (method.getParameterCount() == 3 && method.isAnnotationPresent(AnnotatedCommand.class)) {
				AnnotatedCommand a = method.getAnnotation(AnnotatedCommand.class);
				List<Command> cmds = commands.stream()
						.filter(c -> c.getName().equals(a.name())
								&& c.getDesc().equals(a.desc()))
						.collect(Collectors.toList());

				cmds.forEach(c -> unregisterCommand(c));
			}
		}
	}

}