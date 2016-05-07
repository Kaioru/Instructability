package com.github.kaioru.instructability;

import com.github.kaioru.instructability.command.CommandRegistry;
import com.github.kaioru.instructability.permission.PermissionRegistry;

public class Instructables {

    private static CommandRegistry reg = new CommandRegistry();
    private static PermissionRegistry preg = new PermissionRegistry();

    public static CommandRegistry getRegistry() {
        return reg;
    }

    public static void setRegistry(CommandRegistry reg) {
        Instructables.reg = reg;
    }

	public static PermissionRegistry getPermissionRegistry() {
		return preg;
    }

	public static void setPermissionRegistry(PermissionRegistry preg) {
		Instructables.preg = preg;
    }

}
