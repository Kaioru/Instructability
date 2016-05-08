package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.Defaults;
import com.github.kaioru.instructability.Instructables;
import com.github.kaioru.instructability.command.Command;
import com.github.kaioru.instructability.command.CommandImpl;
import com.github.kaioru.instructability.discord4j.helper.Discord4JHelpCommand;
import com.github.kaioru.instructability.util.PermissionUtil;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.MessageBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Discord4JCommand extends CommandImpl implements Discord4JCommandExecutor {

	public Discord4JCommand() {
		registerPreVerifier((Discord4JCommandVerifier) (args, event, msg) ->
				event.getMessage().getAuthor().getRolesForGuild(event.getMessage().getGuild())
						.stream()
						.anyMatch(role -> getDiscordPermission()
								.hasPermission(Permissions.generatePermissionsNumber(role.getPermissions()), true))
		);
		registerPreVerifier((Discord4JCommandVerifier) (args, event, msg) -> {
			if (getPermission().equals(Defaults.PERMISSION))
				return true;

			String guildId = event.getMessage().getGuild().getID();
			String userId = event.getMessage().getAuthor().getID();

			return Instructables.getPermissionRegistry()
					.getPermissions(String.format("%s:%s", guildId, userId))
					.get()
					.stream()
					.anyMatch(s -> PermissionUtil.checkPermission(s, getPermission()))
					|| event.getMessage().getAuthor().getRolesForGuild(event.getMessage().getGuild())
					.stream()
					.anyMatch(role ->
							Instructables.getPermissionRegistry()
									.getPermissions(String.format("%s:%s", guildId, role.getID()))
									.get()
									.stream()
									.anyMatch(s -> PermissionUtil.checkPermission(s, getPermission())));
		});

		if (!allowPrivateMessage())
			registerPreVerifier((Discord4JCommandVerifier) (args, event, msg) ->
					!event.getMessage().getChannel().isPrivate());

		if (removeTriggerMessage())
			registerPostVerifier((Discord4JCommandVerifier) (args, event, msg) -> {
				event.getMessage().delete();
				return true;
			});

		if (addHelperCommands()) {
			registerCommand(new Discord4JHelpCommand(this));
		}
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
						method.invoke(object, args, event, msg);
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

	public boolean allowPrivateMessage() {
		return Defaults.ALLOW_PRIVATE_MESSAGE;
	}

	public boolean removeTriggerMessage() {
		return Defaults.REMOVE_TRIGGER_MESSAGE;
	}

	public Permissions getDiscordPermission() {
		return Permissions.SEND_MESSAGES;
	}

	@Override
	public void execute(LinkedList<String> args) throws Exception {}

}