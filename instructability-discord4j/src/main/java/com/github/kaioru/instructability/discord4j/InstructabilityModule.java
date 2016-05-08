package com.github.kaioru.instructability.discord4j;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.modules.IModule;

public class InstructabilityModule implements IModule {

	@Override
	public boolean enable(IDiscordClient client) {
		return false;
	}

	@Override
	public void disable() {

	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getAuthor() {
		return null;
	}

	@Override
	public String getVersion() {
		return null;
	}

	@Override
	public String getMinimumDiscord4JVersion() {
		return null;
	}

}
