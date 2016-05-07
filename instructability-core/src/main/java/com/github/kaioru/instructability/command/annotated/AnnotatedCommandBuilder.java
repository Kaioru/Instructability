package com.github.kaioru.instructability.command.annotated;

import com.github.kaioru.instructability.command.CommandBuilder;

import java.util.LinkedList;

public class AnnotatedCommandBuilder extends CommandBuilder<AnnotatedCommand, AnnotatedCommandExecutable> {

	public AnnotatedCommandBuilder(String name) {
		super(name);
	}

	@Override
	public AnnotatedCommand build(AnnotatedCommandExecutable annotatedCommandExecutable) {
		return new AnnotatedCommandImpl() {

			@Override
			public String getName() {
				return name;
			}

			@Override
			public String getDesc() {
				return desc;
			}

			@Override
			public void onCommand(LinkedList<String> args, Object... params) throws Exception {
				annotatedCommandExecutable.execute(args, params);
			}

		};
	}

}
