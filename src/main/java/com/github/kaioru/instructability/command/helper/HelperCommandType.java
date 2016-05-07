package com.github.kaioru.instructability.command.helper;

public enum HelperCommandType {

	INFO(2),
	HELP(4),
	ALIAS(8),
	ALL(INFO.getValue()
			| HELP.getValue()
			| ALIAS.getValue());

	private final int value;

	HelperCommandType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
