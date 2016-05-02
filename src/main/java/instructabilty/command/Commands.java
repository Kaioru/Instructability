package instructabilty.command;

import instructabilty.command.builder.BuiltCommand;
import instructabilty.command.builder.CommandBuilder;
import sx.blah.discord.util.MessageBuilder.Styles;

public class Commands {

	public static BuiltCommand getHelpCommand(Command p) {
		return new CommandBuilder("help")
				.alias("?")
				.noHelperCommands()
				.build((event, msg, args) -> {
					msg.appendContent("Commands:\r\n\r\n", Styles.BOLD);

					p.getCommands()
							.stream()
							//.filter(?)
							.forEach(sub -> {
								StringBuilder content = new StringBuilder();

								content.append("• ");
								content.append(sub.getName());
								content.append("\t");
								content.append(sub.getDesc());

								msg.appendContent(content.toString(),
										Styles.INLINE_CODE)
										.appendContent("\r\n");
							});

					msg.appendContent("\r\n");
					msg.appendContent("For more detailed information use ")
							.appendContent("[command] help",
									Styles.INLINE_CODE);
					msg.send();
				});
	}

	public static BuiltCommand getAliasCommand(Command p) {
		return new CommandBuilder("alias")
				.noHelperCommands()
				.build((event, msg, args) -> {
					msg.appendContent("Aliases:\r\n\r\n", Styles.BOLD);

					p.getCommands()
							.forEach(sub -> {
								StringBuilder content = new StringBuilder();
								String list = String.join(", ",
										sub.getAliases());

								content.append("• ");
								content.append(sub.getName());
								content.append("\t");
								content.append(list);

								msg.appendContent(content.toString(),
										Styles.INLINE_CODE)
										.appendContent("\r\n");
							});

					msg.appendContent("\r\n");
					msg.appendContent("For more detailed information use ")
							.appendContent("[command] alias",
									Styles.INLINE_CODE);
					msg.send();
				});
	}

}
