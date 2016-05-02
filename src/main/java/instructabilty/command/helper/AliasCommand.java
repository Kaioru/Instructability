package instructabilty.command.helper;

import instructabilty.command.Command;
import instructabilty.command.CommandExecutable;
import sx.blah.discord.util.MessageBuilder;

public class AliasCommand extends HelperCommand {

	public AliasCommand(Command parent) {
		super(parent);
	}

	@Override
	public String getName() {
		return "alias";
	}

	@Override
	public String getDesc() {
		return "";
	}

	@Override
	public CommandExecutable getExecutable() {
		return ((event, msg, args) -> {
			msg.appendContent("Aliases:\r\n\r\n", MessageBuilder.Styles.BOLD);

			getParent().getCommands()
					.forEach(sub -> {
						StringBuilder content = new StringBuilder();
						String list = String.join(", ",
								sub.getAliases());

						content.append("• ");
						content.append(sub.getName());
						content.append("\t");
						content.append(list);

						msg.appendContent(content.toString(),
								MessageBuilder.Styles.INLINE_CODE)
								.appendContent("\r\n");
					});

			msg.appendContent("\r\n");
			msg.appendContent("For more detailed information use ")
					.appendContent("[command] alias",
							MessageBuilder.Styles.INLINE_CODE);
			msg.send();
		});
	}

}
