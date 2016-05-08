package com.github.kaioru.instructability.discord4j;

import com.github.kaioru.instructability.Instructables;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.modules.IModule;

public class InstructabilityModule implements IModule {

	private static InstructabilityModule instance;

	private IDiscordClient client;
	private Discord4JCommandListener listener = new Discord4JCommandListener();

	public static InstructabilityModule getInstance() {
		return instance;
	}

	@Override
	public boolean enable(IDiscordClient client) {
		InstructabilityModule.instance = this;
		this.client = client;

		client.getDispatcher().registerListener(listener);
		Instructables.LOGGER.info(getName() + " Discord4J Command Listener registered");
		return true;
	}

	@Override
	public void disable() {
		client.getDispatcher().unregisterListener(listener);
		Instructables.LOGGER.info(getName() + " Discord4J Command Listener unregistered");
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
		return "2.4.6";
	}

}
