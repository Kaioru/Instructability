package com.github.kaioru.instructability.javacord;

import com.github.kaioru.instructability.Defaults;
import com.github.kaioru.instructability.Instructables;
import com.github.kaioru.instructability.command.Command;
import com.github.kaioru.instructability.command.CommandImpl;
import com.github.kaioru.instructability.javacord.helper.JavacordHelpCommand;
import com.github.kaioru.instructability.util.PermissionUtil;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.message.Message;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class JavacordCommand extends CommandImpl implements JavacordCommandExecutor {

	public JavacordCommand() {
		registerPreVerifier((JavacordCommandVerifier) (args, discordAPI, message) -> {
			if (getPermission().equals(Defaults.PERMISSION))
				return true;

			String guildId = message.getChannelReceiver().getServer().getId();
			String userId = message.getAuthor().getId();

			return Instructables.getPermissionRegistry()
					.getPermissions(String.format("%s:%s", guildId, userId))
					.get()
					.stream()
					.anyMatch(s -> PermissionUtil.checkPermission(s, getPermission()))
					|| message.getAuthor().getRoles(message.getChannelReceiver().getServer())
					.stream()
					.anyMatch(role ->
							Instructables.getPermissionRegistry()
									.getPermissions(String.format("%s:%s", guildId, role.getId()))
									.get()
									.stream()
									.anyMatch(s -> PermissionUtil.checkPermission(s, getPermission()))
					);
		});

		if (!allowPrivateMessage())
			registerPreVerifier((JavacordCommandVerifier) (args, discordAPI, message) ->
					!message.isPrivateMessage());

		if (removeTriggerMessage())
			registerPostVerifier((JavacordCommandVerifier) (args, discordAPI, message) -> {
				message.delete();
				return true;
			});

		if (addHelperCommands()) {
			registerCommand(new JavacordHelpCommand(this));
		}
	}

	public JavacordCommand registerCommands(Object object) throws InvocationTargetException, IllegalAccessException {
		for (Method method : object.getClass().getMethods()) {
			if (method.isAnnotationPresent(JavacordAnnotatedCommand.class)) {
				JavacordAnnotatedCommand a = method.getAnnotation(JavacordAnnotatedCommand.class);

				registerCommand(new JavacordCommand() {

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
					public void execute(LinkedList<String> args, DiscordAPI discordAPI, Message message) throws Exception {
						method.invoke(object, args, discordAPI, message);
					}

				});
			}
			if (method.isAnnotationPresent(JavacordAnnotatedReference.class))
				registerCommand((JavacordCommand) method.invoke(object));
		}
		return this;
	}

	public JavacordCommand unregisterCommands(Object object) throws InvocationTargetException, IllegalAccessException {
		for (Method method : object.getClass().getDeclaredMethods()) {
			List<Command> toRemove = new ArrayList<>();
			if (method.isAnnotationPresent(JavacordAnnotatedCommand.class)) {
				JavacordAnnotatedCommand a = method.getAnnotation(JavacordAnnotatedCommand.class);
				toRemove = getCommands().stream()
						.filter(c -> c.getName().equals(a.name())
								&& c.getDesc().equals(a.desc()))
						.collect(Collectors.toList());
			}
			if (method.isAnnotationPresent(JavacordAnnotatedReference.class)) {
				Command cmd = (Command) method.invoke(object);
				toRemove = getCommands().stream()
						.filter(c -> c.getName().equals(cmd.getName())
								&& c.getDesc().equals(cmd.getDesc()))
						.collect(Collectors.toList());
			}
			toRemove.forEach(this::unregisterCommand);
		}
		return this;
	}

	public boolean allowPrivateMessage() {
		return Defaults.ALLOW_PRIVATE_MESSAGE;
	}

	public boolean removeTriggerMessage() {
		return Defaults.REMOVE_TRIGGER_MESSAGE;
	}

	@Override
	public void execute(LinkedList<String> args) throws Exception {}

}
