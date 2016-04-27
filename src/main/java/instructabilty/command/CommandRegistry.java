package instructabilty.command;

import java.util.LinkedList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

public class CommandRegistry extends Command {
	
	private String commandPrefix = ".";

	public CommandRegistry() {
		super(new CommandBuilder(".").buildOptions());
	}

	public void execute(
			MessageReceivedEvent event,
			MessageBuilder msg,
			String input)
			throws Exception {
		final LinkedList<String> args = new LinkedList<>();
		final Pattern p = Pattern
				.compile("([\"'])(?:(?=(\\\\?))\\2.)*?\\1|([^\\s]+)");
		final Matcher m = p.matcher(input);

		while (m.find()) {
			String s = m.group();

			if (s.startsWith("\'") || s.startsWith("\""))
				s = s.substring(1, s.length() - 1);

			args.add(s.toLowerCase());
		}

		execute(event, msg, args);
	}

	@Override
	public void execute(
			MessageReceivedEvent event,
			MessageBuilder msg,
			LinkedList<String> args)
			throws Exception {
		Optional<Command> cmd = getCommand(args.getFirst());

		if (cmd.isPresent())
			super.execute(event, msg, args);
	}

	public String getCommandPrefix() {
		return commandPrefix;
	}

	public void setCommandPrefix(String commandPrefix) {
		this.commandPrefix = commandPrefix;
	}

}
