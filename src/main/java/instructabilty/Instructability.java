package instructabilty;

import instructabilty.command.CommandListener;
import sx.blah.discord.Discord4J;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.modules.IModule;

public class Instructability implements IModule {

	private static Instructability instance;
	private IDiscordClient client;
	private CommandListener listener = new CommandListener();

	public static Instructability getInstance() {
		return instance;
	}

	@Override
	public boolean enable(IDiscordClient client) {
		Instructability.instance = this;
		this.client = client;

		client.getDispatcher().registerListener(listener);
		Discord4J.LOGGER.info(getName() + " Command Listener registered");
		return true;
	}

	@Override
	public void disable() {
		client.getDispatcher().unregisterListener(listener);
		Discord4J.LOGGER.info(getName() + " BuiltCommand Listener unregistered");
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
