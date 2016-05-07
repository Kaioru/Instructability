package com.github.kaioru.instructability.command;

import java.util.LinkedList;

@FunctionalInterface
public interface CommandVerifier {

	boolean verify(LinkedList<String> args);

}
