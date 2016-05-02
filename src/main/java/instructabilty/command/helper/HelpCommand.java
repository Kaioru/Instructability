package instructabilty.command.helper;

import instructabilty.command.Command;
import instructabilty.command.CommandExecutable;
import sx.blah.discord.util.MessageBuilder;

public class HelpCommand extends HelperCommand {

	public HelpCommand(Command parent) {
		super(parent);
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getDesc() {
		return "";
	}

	@Override
	public CommandExecutable getExecutable() {
		return ((event, msg, args) -> {
			msg.appendContent("Commands:\r\n\r\n", MessageBuilder.Styles.BOLD);

			getParent().getCommands()
					.stream()
					.filter(c -> !(c instanceof HelpCommand))
					.forEach(sub -> {
						StringBuilder content = new StringBuilder();

						content.append("• ");
						content.append(sub.getName());
						content.append("\t");
						content.append(sub.getDesc());

						msg.appendContent(content.toString(),
								MessageBuilder.Styles.INLINE_CODE)
								.appendContent("\r\n");
					});

			msg.appendContent("\r\n");
			msg.appendContent("For more detailed information use ")
					.appendContent("[command] help",
							MessageBuilder.Styles.INLINE_CODE);
			msg.send();
		});
	}

}