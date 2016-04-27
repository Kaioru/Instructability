package instructabilty.command;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Optional;

import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

public class Command implements CommandExecutable {

	private final CommandOptions options;

	public Command(CommandOptions options) {
		this.options = options;

		if (options.isHookDefaults()) {
			options.getSubcommands().add(Commands.getHelpCommand(this));
			options.getSubcommands().add(Commands.getAliasCommand(this));
		}
	}

	public void clearCommands() {
		getOptions().getSubcommands().clear();
	}

	public void deregisterCommand(Command cmd) {
		getOptions().getSubcommands().remove(cmd);
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
			getOptions().getExecutable().execute(event, msg, args);
		}
	}

	public Optional<Command> getCommand(String name) {
		return options.getSubcommands().stream().filter(cmd -> {
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
		getOptions().getSubcommands().add(cmd);
	}

}
