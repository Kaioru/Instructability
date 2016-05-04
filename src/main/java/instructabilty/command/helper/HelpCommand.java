package instructabilty.command.helper;

import instructabilty.command.Command;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
	public void onExecute(MessageReceivedEvent event, MessageBuilder msg, LinkedList<String> args) throws Exception {
		List<Command> list = getParent().getCommands()
				.stream()
				.filter(c -> !(c instanceof HelperCommand))
				.collect(Collectors.toList());

		if (list.size() > 0) {
			msg.appendContent("Commands:\r\n\r\n", MessageBuilder.Styles.BOLD);
			list.forEach(sub -> {
				StringBuilder content = new StringBuilder();

				content.append("• ");
				content.append(sub.getName());
				content.append("\t");
				content.append(sub.getDesc());

				msg.appendContent(content.toString(),
						MessageBuilder.Styles.INLINE_CODE)
						.appendContent("\r\n");
			});
		} else msg.appendContent("No commands found!");

		msg.appendContent("\r\n");
		msg.appendContent("For more detailed information use ")
				.appendContent("[command] help",
						MessageBuilder.Styles.INLINE_CODE);
		msg.send();
	}

}