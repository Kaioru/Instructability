package com.github.kaioru.instructability.jda;

import com.github.kaioru.instructability.Defaults;
import com.github.kaioru.instructability.Instructables;
import com.github.kaioru.instructability.command.Command;
import com.github.kaioru.instructability.command.CommandImpl;
import com.github.kaioru.instructability.jda.helper.JDAHelpCommand;
import com.github.kaioru.instructability.util.PermissionUtil;
import net.dv8tion.jda.Permission;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class JDACommand extends CommandImpl implements JDACommandExecutor {

	public JDACommand() {
		registerPreVerifier((JDACommandVerifier) (args, event) ->
				event.getGuild().getRolesForUser(event.getAuthor())
						.stream()
						.anyMatch(r -> r.hasPermission(getDiscordPermission()))
		);
		registerPreVerifier((JDACommandVerifier) (args, event) -> {
			if (getPermission().equals(Defaults.PERMISSION))
				return true;

			String guildId = event.getGuild().getId();
			String userId = event.getAuthor().getId();

			return Instructables.getPermissionRegistry()
					.getPermissions(String.format("%s:%s", guildId, userId))
					.get()
					.stream()
					.anyMatch(s -> PermissionUtil.checkPermission(s, getPermission()))
					|| event.getGuild().getRolesForUser(event.getAuthor())
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
			registerPreVerifier((JDACommandVerifier) (args, event) ->
					!event.isPrivate());

		if (removeTriggerMessage())
			registerPostVerifier((JDACommandVerifier) (args, event) -> {
				event.getMessage().deleteMessage();
				return true;
			});

		if (addHelperCommands()) {
			registerCommand(new JDAHelpCommand(this));
		}
	}

	public JDACommand registerCommands(Object object) throws InvocationTargetException, IllegalAccessException {
		for (Method method : object.getClass().getMethods()) {
			if (method.isAnnotationPresent(JDAAnnotatedCommand.class)) {
				JDAAnnotatedCommand a = method.getAnnotation(JDAAnnotatedCommand.class);

				registerCommand(new JDACommand() {

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
					public void execute(LinkedList<String> args, MessageReceivedEvent event) throws Exception {
						method.invoke(object, args, event);
					}

				});
			}
			if (method.isAnnotationPresent(JDAAnnotatedReference.class))
				registerCommand((JDACommand) method.invoke(object));
		}
		return this;
	}

	public JDACommand unregisterCommands(Object object) throws InvocationTargetException, IllegalAccessException {
		for (Method method : object.getClass().getDeclaredMethods()) {
			List<Command> toRemove = new ArrayList<>();
			if (method.isAnnotationPresent(JDAAnnotatedCommand.class)) {
				JDAAnnotatedCommand a = method.getAnnotation(JDAAnnotatedCommand.class);
				toRemove = getCommands().stream()
						.filter(c -> c.getName().equals(a.name())
								&& c.getDesc().equals(a.desc()))
						.collect(Collectors.toList());
			}
			if (method.isAnnotationPresent(JDAAnnotatedReference.class)) {
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

	public Permission getDiscordPermission() {
		return Permission.MESSAGE_WRITE;
	}

	@Override
	public void execute(LinkedList<String> args) throws Exception {}

}
