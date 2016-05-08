package com.github.kaioru.instructability.util;

import com.github.kaioru.instructability.command.Command;
import com.github.kaioru.instructability.command.CommandRegistry;
import com.github.kaioru.instructability.command.CommandVerifier;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandUtil {

	public static LinkedList<String> getArgsFromText(String text) {
		final LinkedList<String> args = new LinkedList<>();
		final Pattern p = Pattern.compile("([\"'])(?:(?=(\\\\?))\\2.)*?\\1|([^\\s]+)");
		final Matcher m = p.matcher(text);

		while (m.find()) {
			String s = m.group();

			if (s.startsWith("\'") || s.startsWith("\""))
				s = s.substring(1, s.length() - 1);
			args.add(s.toLowerCase());
		}
		return args;
	}

	public static void executeCommand(Command cmd, LinkedList<String> args, Object... params) throws Exception {
		if (args.size() > 0) {
			String first = args.removeFirst();
			Optional<Command> opt = cmd.getCommand(first)
					.stream()
					.findFirst();
			if (opt.isPresent()) {
				executeCommand(opt.get(), args, params);
				return;
			}
			args.addFirst(first);
		}

		if (!(cmd instanceof CommandRegistry)) {
			if (params.length == 0) {
				if (cmd.getPreVerifiers().stream().allMatch(v -> v.verify(args)))
					cmd.execute(args);
				cmd.getPostVerifiers().forEach(v -> v.verify(args));
			} else {
				CommandUtil.getMethod(cmd.getClass(), "execute", params.length + 1)
						.ifPresent(m -> {
							try {
								List<Object> list = new ArrayList<>();

								list.add(args);
								Arrays.stream(params).forEach(list::add);

								Predicate<CommandVerifier> predicate = (v -> {
									Optional<Method> opt = CommandUtil.getMethod(v.getClass(), "verify", params.length + 1);

									if (opt.isPresent()) {
										try {
											Method verify = opt.get();
											verify.setAccessible(true);
											return (boolean) verify.invoke(v, list.toArray());
										} catch (Exception e) { e.printStackTrace(); }
									}
									return true;
								});

								if (cmd.getPreVerifiers().stream().allMatch(predicate)) {
									m.setAccessible(true);
									m.invoke(cmd, list.toArray());
								}

								cmd.getPostVerifiers().stream().allMatch(predicate);
							} catch (Exception e) {
								e.printStackTrace();
							}
						});
			}
		}
	}

	public static Optional<Method> getMethod(Class classz, String name, int paramsCount) {
		return Arrays.asList(classz.getMethods())
				.stream()
				.filter(m -> m.getName().equals(name))
				.filter(m -> m.getParameterCount() == paramsCount)
				.findFirst();
	}

}
