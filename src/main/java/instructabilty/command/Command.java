package instructabilty.command;

import instructabilty.Instructables;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.modules.IModule;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface Command extends CommandExecutable {

	String getName();

	String getDesc();

	List<String> getAliases();

	List<Command> getCommands();

	default Optional<Command> getCommand(String name) {
		return getCommands().stream()
				.filter(cmd -> cmd.getName().startsWith(name)
						|| cmd.getAliases().stream().anyMatch(s -> s.startsWith(name)))
				.findFirst();
	}

	default Optional<IModule> getModule() {
		return Optional.empty();
	}

	default Optional<CommandPermission> getPermission() {
		return Optional.empty();
	}

	CommandExecutable getExecutable();

	default void addHelperCommands() {
		getCommands().add(Commands.getHelpCommand(this));
		getCommands().add(Commands.getAliasCommand(this));
	}

	@Override
	default void execute(
			MessageReceivedEvent event,
			MessageBuilder msg,
			LinkedList<String> args) throws Exception {
		try {
			Optional<Command> opt = getCommand(args.getFirst());

			if (opt.isPresent()) {
				args.removeFirst();
				opt.get().execute(event, msg, args);
			} else
				throw new NoSuchElementException();
		} catch (NoSuchElementException e) {
			if (getPermission().isPresent()) {
				CommandPermission reqPermission = getPermission().get();

				if (!Instructables.getPermissionRegistry()
						.getForGuild(event.getMessage().getGuild().getID())
						.checkPermissions(event.getMessage().getAuthor(), event.getMessage().getGuild(), reqPermission)) {
					msg.appendContent(String.format("You do not have the permission '%s' to use the command.", reqPermission.getName()));
					msg.build();
					return;
				}
			}

			getExecutable().execute(event, msg, args);
		}
	}

}
