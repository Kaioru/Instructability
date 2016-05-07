package com.github.kaioru.instructability.command;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandRegistry extends CommandImpl {

	@Override
	public String getName() {
		return "registry";
	}

	@Override
	public String getDesc() {
		return "The root command registry";
	}

	@Override
	public void onCommand(LinkedList<String> args) throws Exception {}

	public LinkedList<String> getArgsFromMessage(String msg) {
		final LinkedList<String> args = new LinkedList<>();
		final Pattern p = Pattern.compile("([\"'])(?:(?=(\\\\?))\\2.)*?\\1|([^\\s]+)");
		final Matcher m = p.matcher(msg);

		while (m.find()) {
			String s = m.group();

			if (s.startsWith("\'") || s.startsWith("\""))
				s = s.substring(1, s.length() - 1);
			args.add(s.toLowerCase());
		}
		return args;
	}

	public void execute(String input) throws Exception {
		this.execute(getArgsFromMessage(input));
	}

}
