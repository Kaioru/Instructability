package instructabilty.command;

import java.util.stream.Stream;

import sx.blah.discord.util.MessageBuilder.Styles;

public class Commands {

	public static Command getHelpCommand(Command p) {
		return new CommandBuilder("help")
				.alias("?")
				.skipHookingDefaults()
				.build((event, msg, args) -> {
					msg.appendContent("Commands:\r\n\r\n", Styles.BOLD);

					Stream<Command> stream = p.getOptions()
							.getSubcommands()
							.stream()
							.filter(cmd -> cmd.getOptions().isHookDefaults());

					stream.forEach(sub -> {
						StringBuilder content = new StringBuilder();
						CommandOptions opt = sub.getOptions();

						content.append("• ");
						content.append(opt.getName());
						content.append("\t");
						content.append(opt.getDesc());

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

	public static Command getAliasCommand(Command p) {
		return new CommandBuilder("alias")
				.skipHookingDefaults()
				.build((event, msg, args) -> {
					msg.appendContent("Aliases:\r\n\r\n", Styles.BOLD);

					p.getOptions()
							.getSubcommands()
							.forEach(sub -> {
								StringBuilder content = new StringBuilder();
								CommandOptions opt = sub.getOptions();
								String list = String.join(", ",
										opt.getAliases());

								content.append("• ");
								content.append(opt.getName());
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
