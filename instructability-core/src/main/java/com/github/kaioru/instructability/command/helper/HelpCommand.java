package com.github.kaioru.instructability.command.helper;

import com.github.kaioru.instructability.Defaults;
import com.github.kaioru.instructability.Instructables;
import com.github.kaioru.instructability.command.Command;

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
		return Defaults.DESCRIPTION;
	}

	@Override
	public void execute(LinkedList<String> args) throws Exception {
		Instructables.LOGGER.info(getCommandList());
	}

	public String getCommandList() {
		StringBuilder builder = new StringBuilder();
		List<Command> list = getParent().getCommands()
				.stream()
				.filter(c -> !(c instanceof HelperCommand))
				.collect(Collectors.toList());

		if (list.size() > 0) {
			builder.append(getParent().getName()).append(" Commands:\r\n\r\n");
			list.forEach(sub -> {
				StringBuilder content = new StringBuilder();

				content.append("• ");
				content.append(sub.getName());
				content.append("\t");
				content.append(sub.getDesc());

				builder.append(content.toString()).append("\r\n");
			});
		}

		return builder.toString();
	}

}
