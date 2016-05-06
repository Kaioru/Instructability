package instructability;

import instructability.command.CommandRegistry;
import instructability.permission.PermissionRegistry;

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
