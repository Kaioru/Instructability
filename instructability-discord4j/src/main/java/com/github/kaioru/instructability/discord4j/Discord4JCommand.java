package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.Instructables;
import com.github.kaioru.instructability.command.Command;
import com.github.kaioru.instructability.command.CommandImpl;
import com.github.kaioru.instructability.util.PermissionUtil;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Discord4JCommand extends CommandImpl implements Discord4JCommandExecutor {

	public Discord4JCommand() {
		registerPreVerifier((Discord4JCommandVerifier) (args, event, msg) -> {
			String guildId = event.getMessage().getGuild().getID();
			String userId = event.getMessage().getAuthor().getID();

			return Instructables.getPermissionRegistry()
					.getPermissions(String.format("%s:%s", guildId, userId))
					.get()
					.stream()
					.anyMatch(s -> PermissionUtil.checkPermission(s, getPermission()))
					|| event.getMessage().getAuthor().getRolesForGuild(event.getMessage().getGuild())
					.stream()
					.anyMatch(roleId ->
							Instructables.getPermissionRegistry()
									.getPermissions(String.format("%s:%s", guildId, roleId))
									.get()
									.stream()
									.anyMatch(s -> PermissionUtil.checkPermission(s, getPermission()))
					);
		});

		if (!allowPrivateMessage())
			registerPreVerifier((Discord4JCommandVerifier) (args, event, msg) ->
					!event.getMessage().getChannel().isPrivate());

		if (removeTriggerMessage())
			registerPostVerifier((Discord4JCommandVerifier) (args, event, msg) -> {
				event.getMessage().delete();
				return true;
			});
	}

	public void registerCommands(Object object) throws InvocationTargetException, IllegalAccessException {
		for (Method method : object.getClass().getMethods()) {
			if (method.isAnnotationPresent(Discord4JAnnotatedCommand.class)) {
				Discord4JAnnotatedCommand a = method.getAnnotation(Discord4JAnnotatedCommand.class);

				registerCommand(new Discord4JCommand() {

					@Override
					public String getName() {
						return a.name();
					}

					@Override
					public String getDesc() {
						return a.desc();
					}

					@Override
					public String getPermission() { return a.perm(); }

					@Override
					public boolean allowPrivateMessage() {
						return a.allowPrivateMessage();
					}

					@Override
					public boolean removeTriggerMessage() {
						return a.removeTriggerMessage();
					}

					@Override
					public void execute(LinkedList<String> args, MessageReceivedEvent event, MessageBuilder msg) throws Exception {
						method.invoke(this, args, event, msg);
					}

				});
			}
			if (method.isAnnotationPresent(Discord4JAnnotatedReference.class))
				registerCommand((Command) method.invoke(this));
		}
	}

	public void unregisterCommands(Object object) throws InvocationTargetException, IllegalAccessException {
		for (Method method : object.getClass().getDeclaredMethods()) {
			List<Command> toRemove = new ArrayList<>();
			if (method.isAnnotationPresent(Discord4JAnnotatedCommand.class)) {
				Discord4JAnnotatedCommand a = method.getAnnotation(Discord4JAnnotatedCommand.class);
				toRemove = getCommands().stream()
						.filter(c -> c.getName().equals(a.name())
								&& c.getDesc().equals(a.desc()))
						.collect(Collectors.toList());
			}
			if (method.isAnnotationPresent(Discord4JAnnotatedReference.class)) {
				Command cmd = (Command) method.invoke(this);
				toRemove = getCommands().stream()
						.filter(c -> c.getName().equals(cmd.getName())
								&& c.getDesc().equals(cmd.getDesc()))
						.collect(Collectors.toList());
			}
			toRemove.forEach(this::unregisterCommand);
		}
	}

	public abstract boolean allowPrivateMessage();

	public abstract boolean removeTriggerMessage();

	@Override
	public void execute(LinkedList<String> args) throws Exception {}

}