package instructabilty.command;

import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandRegistry implements Command {

	private String commandPrefix = ".";

	public CommandRegistry() {
		addHelperCommands();
	}

	@Override
	public String getName() {
		return "registry";
	}

	@Override
	public String getDesc() {
		return "The root command registry";
	}

	@Override
	public CommandExecutable getExecutable() {
		return ((event, msg, args) -> {});
	}

	public void execute(
			MessageReceivedEvent event,
			MessageBuilder msg,
			String input)
			throws Exception {
		final LinkedList<String> args = new LinkedList<>();
		final Pattern p = Pattern.compile("([\"'])(?:(?=(\\\\?))\\2.)*?\\1|([^\\s]+)");
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
			execute(event, msg, args);
	}

	public String getCommandPrefix() {
		return commandPrefix;
	}

	public void setCommandPrefix(String commandPrefix) {
		this.commandPrefix = commandPrefix;
	}

}
