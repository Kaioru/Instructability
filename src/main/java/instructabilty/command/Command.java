package instructabilty.command;

import instructabilty.Instructables;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Command implements CommandExecutable {

	private final CommandOptions options;

	public Command(CommandOptions options) {
		this.options = options;

		if (options.isHookDefaults()) {
			options.getSubCommands().add(Commands.getHelpCommand(this));
			options.getSubCommands().add(Commands.getAliasCommand(this));
		}
	}

	public void clearCommands() {
		getOptions().getSubCommands().clear();
	}

	public void deregisterCommand(Command cmd) {
		getOptions().getSubCommands().remove(cmd);
	}

	@Override
	public void execute(
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
			if (getOptions().getPermission().isPresent()) {
				CommandPermission reqPermission = getOptions().getPermission().get();

				if (!Instructables.getPermissionRegistry()
						.getForGuild(event.getMessage().getGuild().getID())
						.checkPermissions(event.getMessage().getAuthor(), event.getMessage().getGuild(), reqPermission)) {
					msg.appendContent("You do not have enough permissions to use the command.");
					msg.build();
					return;
				}
			}

			getOptions().getExecutable().execute(event, msg, args);
		}
	}

	public Optional<Command> getCommand(String name) {
		return options.getSubCommands().stream().filter(cmd -> {
			CommandOptions opt = cmd.getOptions();

			return opt.getName().startsWith(name)
					|| opt.getAliases().stream()
					.anyMatch(s -> s.startsWith(name));
		}).findFirst();
	}

	public CommandOptions getOptions() {
		return options;
	}

	public void registerCommand(Command cmd) {
		getOptions().getSubCommands().add(cmd);
	}

}
