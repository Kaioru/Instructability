package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.command.Instructables;
import com.github.kaioru.instructability.discord4j.permission.PermissionRegistry;
import sx.blah.discord.Discord4J;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.modules.IModule;

public class Discord4JInstructability implements IModule {

	private static Discord4JInstructability instance;

	private IDiscordClient client;
	private PermissionRegistry preg = new PermissionRegistry();
	private Discord4JCommandListener listener = new Discord4JCommandListener();

	public static Discord4JInstructability getInstance() {
		return instance;
	}

	@Override
	public boolean enable(IDiscordClient client) {
		this.client = client;
		Discord4JInstructability.instance = this;
		Instructables.setRegistry(new Discord4JCommandRegistry());
		client.getDispatcher().registerListener(listener);
		Discord4J.LOGGER.info(getName() + " Command Listener registered");
		return true;
	}

	@Override
	public void disable() {
		client.getDispatcher().unregisterListener(listener);
		Discord4J.LOGGER.info(getName() + " Command Listener unregistered");
	}

	@Override
	public String getName() {
		return getClass().getPackage().getImplementationTitle();
	}

	@Override
	public String getAuthor() {
		return "Kaioru";
	}

	@Override
	public String getVersion() {
		return getClass().getPackage().getImplementationVersion();
	}

	@Override
	public String getMinimumDiscord4JVersion() {
		return "2.4.0";
	}

	public PermissionRegistry getPermissionRegistry() {
		return preg;
	}

}
